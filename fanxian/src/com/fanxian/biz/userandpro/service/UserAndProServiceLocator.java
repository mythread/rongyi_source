package com.fanxian.biz.userandpro.service;

import com.yue.commons.core.CommonServiceLocator;

public class UserAndProServiceLocator extends CommonServiceLocator {

    public static UserAndProService getUserAndProService() {
        return (UserAndProService) getBean("userAndProService");
    }
}
