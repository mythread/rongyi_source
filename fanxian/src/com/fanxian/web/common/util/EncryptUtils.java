package com.fanxian.web.common.util;

import com.yue.commons.nisa.interfaces.ConfigService;
import com.yue.commons.nisa.service.ConfigServiceLocator;

/**
 * 这个类是代理到com.yue.commons.security.EncryptUtils类上的
 * 
 * @author wanghai 2011-12-5 下午11:50:22
 */
public class EncryptUtils {

    private static final String SECRET_KEY;
    static {
        ConfigService configService = ConfigServiceLocator.getCongfigService();
        SECRET_KEY = configService.getKV("S_commons.security.key", null);
        if (SECRET_KEY == null) {
            throw new RuntimeException("从NISA获取加密Key失败了！");
        }
    }

    public static String decrypt(String secretString) {
        return com.yue.commons.security.EncryptUtils.decrypt(secretString, SECRET_KEY);
    }

    public static String encrypt(String source) {
        return com.yue.commons.security.EncryptUtils.encrypt(source, SECRET_KEY);
    }

}
