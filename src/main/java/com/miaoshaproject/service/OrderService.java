package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;

/**
 * @Author: pyh
 * @Date: 2019/5/4 10:45
 * @Version: 1.0
 * @Function:
 * @Description:
 */
public interface OrderService {
    //秒杀活动方案：
    //方案1.通过前端url上传过来秒杀活动id，然后下单接口内校验对应id是否属于对应商品且活动已开始
    //方案2.直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下
    //方案1的效果较好

    //根据用户id，商品id，购买数量创建订单
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId,Integer amount) throws BusinessException;

}
