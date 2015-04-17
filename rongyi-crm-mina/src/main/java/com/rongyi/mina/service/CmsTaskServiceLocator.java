package com.rongyi.mina.service;

/**
 * 类CmsTaskServiceLocator.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月4日 下午2:08:26
 */
public class CmsTaskServiceLocator extends CommonServiceLocator {

    public static CmsTaskService getCmsTaskService() {
        return (CmsTaskService) getBean("cmsTaskService");
    }

    // test
    public static void main(String[] args) {
        CmsTaskService c = getCmsTaskService();
        System.out.println(c);
    }
}
