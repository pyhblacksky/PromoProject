package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ItemModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: pyh
 * @Date: 2019/5/3 19:54
 * @Version: 1.0
 * @Function:
 * @Description:
 * item服务
 */
@Service
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);

}