package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.ItemDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri May 03 21:03:39 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri May 03 21:03:39 CST 2019
     */
    int insert(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri May 03 21:03:39 CST 2019
     */
    int insertSelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri May 03 21:03:39 CST 2019
     */
    ItemDO selectByPrimaryKey(Integer id);

    /**
     * 返回商品列表
     * */
    List<ItemDO> listItem();


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri May 03 21:03:39 CST 2019
     */
    int updateByPrimaryKeySelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri May 03 21:03:39 CST 2019
     */
    int updateByPrimaryKey(ItemDO record);
}