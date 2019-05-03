package com.miaoshaproject.error;

/**
 * @Author: pyh
 * @Date: 2019/5/2 20:03
 * @Version: 1.0
 * @Function:
 * @Description:
 */
public interface CommonError {

    public int getErrorCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);

}
