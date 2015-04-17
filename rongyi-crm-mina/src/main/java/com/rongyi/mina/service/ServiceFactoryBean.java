package com.rongyi.mina.service;

import com.rongyi.mina.common.util.SpringContextUtil;

/**
 * 获得web应用上下文的bean实例
 * 
 * @author jiejie 2014年4月27日 下午2:22:09
 */
public class ServiceFactoryBean extends SpringContextUtil {

    public static CmsTaskService getCmsTaskService() {
        return (CmsTaskService) getBean("cmsTaskService");
    }

    public static OperateMongoService getOperateMongoService() {
        return (OperateMongoService) getBean("operateMongoService");
    }

    public static JobService getJobService() {
        return (JobService) getBean("jobService");
    }

    public static MallIpConfigService getMallIpConfigService() {
        return (MallIpConfigService) getBean("mallIpConfigService");
    }
}
