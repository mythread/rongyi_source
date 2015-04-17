package com.fanxian.web.common.validation;

import org.apache.commons.validator.EmailValidator;

/**
 * 一些数据验证的工具方法
 */
public class ValidationUtil {

    /**
     * email验证
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
