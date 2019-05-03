package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.stereotype.Service;

/**
 * @Author: pyh
 * @Date: 2019/5/2 15:28
 * @Version: 1.0
 * @Function:
 * @Description:
 */
@Service
public interface UserService {

    //通过用户id获取用户的方法
    UserModel getUserById(Integer id);

    //完成对用户注册的整套流程
    void register(UserModel userModel) throws BusinessException;

    //完成对用户登录校验流程
    /**
     * @param telephone:用户注册手机
     * @param encrptPassword:用户加密后的密码
     * */
    UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException;
}
