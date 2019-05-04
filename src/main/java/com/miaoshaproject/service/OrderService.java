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

    //根据用户id，商品id，购买数量创建订单
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;

}
