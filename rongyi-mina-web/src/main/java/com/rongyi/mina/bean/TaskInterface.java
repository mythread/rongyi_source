package com.rongyi.mina.bean;

import java.util.List;

/**
 * @author jiejie 2014年4月4日 上午11:57:07
 */
public interface TaskInterface<T> {

    public static final String MALL_ID          = "mallId";
    public static final String CMS_ID           = "cmsId";
    public static final String JOB_ID           = "jobId";
    public static final String JOB_TYPE         = "type";
    public static final String JOB_CREATE_DATE  = "gmtJobCreate";
    public static final String JOB_ACTION_TYPE  = "actionType";
    public static final String CATEGORY_ID_LIST = "categoryIdList";

    /**
     * 获取相应的商铺、品牌、广告
     */
    T getTask();

    /**
     * 获得task任务
     */
    CmsTask getCmsTask();

    /**
     * 获得图片
     */
    List<Photos> getPhotosList();
}
