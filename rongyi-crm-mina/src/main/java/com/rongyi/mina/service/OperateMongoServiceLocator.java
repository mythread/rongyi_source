package com.rongyi.mina.service;

/**
 * 类OperateMongoServiceLocator.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月15日 下午8:13:20
 */
public class OperateMongoServiceLocator extends CommonServiceLocator {

    public static OperateMongoService getOperateMongoService() {
        return (OperateMongoService) getBean("operateMongoService");
    }

}
