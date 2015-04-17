package com.rongyi.mina.bean;

import java.util.Date;
import java.util.HashMap;

import com.rongyi.mina.constant.Constant;

/**
 * 处理品牌的task
 * 
 * @author jiejie 2014年4月4日 上午11:57:51
 */
public class BrandTaskImpl extends BaseTaskImpl<Brands> {

    public BrandTaskImpl(HashMap<String, Object> map, Class<Brands> clazz) {
        super(map, clazz);
    }

    @Override
    public CmsTask getCmsTask() {
        Brands brand = getTask();
        HashMap<String, Object> map = getMap();
        CmsTask task = new CmsTask();
        task.setActionType((Integer) map.get(JOB_ACTION_TYPE));
        task.setType((Integer) map.get(JOB_TYPE));
        task.setCmsOuterId(map.get(CMS_ID).toString());
        long jobCreate = (Long) map.get(JOB_CREATE_DATE);
        task.setGmtJobCreate(new Date(jobCreate));
        task.setReviewStatus(Constant.SynchState.SYNCHRONOUS_ING);
        task.setTaskStatus(Constant.TaskStatus.UN_HANDLE);
        task.setMallId((String) map.get(MALL_ID));
        task.setMallName((String) map.get(MALL_NAME));
        // TODO:xujie 品牌url
        task.setBrandUrl("");
        if (brand != null) {
            task.setReviewInfo(brand.getName());
        }
        return task;
    }
}
