package com.fanxian.biz.user.service;

import com.yue.commons.core.CommonServiceLocator;

public class UserServiceLocator extends CommonServiceLocator {

    public static UserService getUserService() {
        return (UserService) getBean("userService");
    }
}
