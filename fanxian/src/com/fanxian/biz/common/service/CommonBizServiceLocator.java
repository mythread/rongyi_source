package com.fanxian.biz.common.service;

import com.yue.commons.core.CommonServiceLocator;
import com.yue.commons.db.jdbc.DataSource;

/**
 */
public class CommonBizServiceLocator extends CommonServiceLocator {

    /**
     * @return
     */
    public static CommonBizService getCommonBizService() {
        return (CommonBizService) getBean("commonBizService");
    }

    /**
     * 获取数据源
     * 
     * @return
     */
    public static DataSource getDataSource() {
        return (DataSource) getBean("dataSource");
    }

}
