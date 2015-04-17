package com.rongyi.mina.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring容器加载classpath下的配置文件
 * 
 * @author jiejie 2014年4月4日 下午1:40:24
 */
public class CommonServiceLocator {

    protected static ApplicationContext context;
    private static Logger               log = LoggerFactory.getLogger(CommonServiceLocator.class);

    static {
        try {
            context = new ClassPathXmlApplicationContext(new String[] { "classpath*:resources/spring_*.xml" });
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static boolean hasInitFinish() {
        return context != null;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String beanId) {
        return context.getBean(beanId);
    }
}
