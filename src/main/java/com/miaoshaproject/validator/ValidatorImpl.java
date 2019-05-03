package com.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: pyh
 * @Date: 2019/5/3 16:05
 * @Version: 1.0
 * @Function:
 * @Description:
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);//如果违背规则，set中有值
        if(constraintViolationSet.size() > 0){
            //有错误
            result.setHasErrors(true);
            //遍历,拉姆达表达式
            constraintViolationSet.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();//获取错误信息
                String propertyName = constraintViolation.getPropertyPath().toString();//获取哪个字段错误
                result.getErrorMsgMap().put(propertyName, errMsg);
            });

//            for(ConstraintViolation con : constraintViolationSet){
//                String errMsg = con.getMessage();
//                String propertyName = con.getPropertyPath().toString();
//                result.getErrorMsgMap().put(propertyName, errMsg);
//            }
        }

        return result;
    }

    //Spring bean初始化完成之后会回调对应
    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
