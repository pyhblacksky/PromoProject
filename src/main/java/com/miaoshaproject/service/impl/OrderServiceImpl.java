package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.dataobject.OrderDO;
import com.miaoshaproject.dataobject.SequenceDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: pyh
 * @Date: 2019/5/4 10:47
 * @Version: 1.0
 * @Function:
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId,Integer amount) throws BusinessException {
        //1、校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATIONN_ERROR, "商品信息不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATIONN_ERROR, "用户信息不存在");
        }
        //校验活动信息
        if(promoId != null){
            //(1)校验对应活动是否存在这个适用商品
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATIONN_ERROR,"活动信息不正确");
            } else if(itemModel.getPromoModel().getStatus().intValue() != 2){
                //(2)校验活动是否正在进行中,不在进行中，抛出异常
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATIONN_ERROR,"活动还未开始");
            }
        }

        //考虑超库存时的处理
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATIONN_ERROR, "数量信息不正确");
        }

        //2、落单减库存，或支付减库存    （锁库存？）  此时使用落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            //减库存失败
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3、订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if(promoId != null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());//活动价格
        } else{
            orderModel.setItemPrice(itemModel.getPrice());//正常价格
        }
        orderModel.setPromoId(promoId);//活动信息
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));


        //生成交易流水号，订单号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //加上商品的销量
        itemService.increaseSales(itemId, amount);

        //4、返回前端
        return orderModel;
    }

    //转化函数 orderModel -> orderDO
    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;
    }

    //返回生成的订单号
    //存在回滚问题，事务失败，sequence也不该重复使用，保证全局唯一性，此处关闭事务回滚
    @Transactional(propagation = Propagation.REQUIRES_NEW)//直接提交事务，失败不回滚
    public String generateOrderNo(){
        //假设订单号有16位
        StringBuilder sb = new StringBuilder();

        //前8位位时间信息，年月日
        LocalDateTime now = LocalDateTime.now();//获取当前时间
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        sb.append(nowDate);

        //中间那6位为自增序列，保证订单号不重复
        //在数据库中创建对应的sequence表来获取自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);//更新

        String sequenceStr = String.valueOf(sequence);//sequence应该设置一个最大值，防止超出，设置成一个循环值
        for(int i = 0; i < 6-sequenceStr.length(); i++){
            sb.append("0");//不足六位，补全
        }
        sb.append(sequenceStr);

        //最后2位为分库分表位
        sb.append("00");//暂时写死

        return sb.toString();
    }

    //调试方法
    public static void main(String[] args){
        LocalDateTime now = LocalDateTime.now();//获取当前时间
        System.out.println(now.format(DateTimeFormatter.ISO_DATE).replace("-",""));
        int a = 0;
    }

}
