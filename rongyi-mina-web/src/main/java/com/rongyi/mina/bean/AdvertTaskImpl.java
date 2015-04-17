package com.rongyi.mina.bean;

import java.util.Date;
import java.util.HashMap;

import com.rongyi.mina.constant.Constant;

/**
 * 处理广告的task
 * 
 * @author jiejie 2014年4月4日 下午12:01:45
 */
public class AdvertTaskImpl extends BaseTaskImpl<Advertisements> {

    public AdvertTaskImpl(HashMap<String, Object> map, Class<Advertisements> clazz) {
        super(map, clazz);
    }

    @Override
    public CmsTask getCmsTask() {
        HashMap<String, Object> map = getMap();
        CmsTask task = new CmsTask();
        task.setActionType((Integer) map.get(JOB_ACTION_TYPE));
        task.setType((Integer) map.get(JOB_TYPE));
        long jobCreate = (Long) map.get(JOB_CREATE_DATE);
        task.setGmtJobCreate(new Date(jobCreate));
        task.setReviewStatus(Constant.SynchState.SYNCHRONOUS_ING);
        task.setTaskStatus(Constant.TaskStatus.UN_HANDLE);
        task.setMallId((String) map.get(MALL_ID));
        return task;
    }
}
