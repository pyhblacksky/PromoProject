package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoModel;

/**
 * @Author: pyh
 * @Date: 2019/5/4 16:23
 * @Version: 1.0
 * @Function:
 * @Description:
 *  促销service
 */
public interface PromoService {

    //根据itemid获取即将进行或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);

}
