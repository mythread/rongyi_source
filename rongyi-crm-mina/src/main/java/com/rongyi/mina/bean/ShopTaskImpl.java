package com.rongyi.mina.bean;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongyi.mina.constant.Constant;

/**
 * 处理商铺的task
 * 
 * @author jiejie 2014年4月4日 上午11:59:59
 */
public class ShopTaskImpl extends BaseTaskImpl<Shops> {

    private static final Logger log = LoggerFactory.getLogger(ShopTaskImpl.class);

    public ShopTaskImpl(HashMap<String, Object> map, Class<Shops> clazz) {
        super(map, clazz);
    }

    @Override
    public CmsTask getCmsTask() {
        Shops shop = getTask();
        HashMap<String, Object> map = getMap();
        CmsTask task = new CmsTask();
        task.setActionType((Integer) map.get(JOB_ACTION_TYPE));
        task.setType((Integer) map.get(JOB_TYPE));
        task.setCmsOuterId(map.get(CMS_ID).toString());
        long jobCreate = (Long) map.get(JOB_CREATE_DATE);
        task.setGmtJobCreate(new Date(jobCreate));
        task.setReviewStatus(Constant.SynchState.SYNCHRONOUS_ING);
        task.setTaskStatus(Constant.TaskStatus.UN_HANDLE);
        task.setMallName((String) map.get(MALL_NAME));
        task.setMallId((String) map.get(MALL_ID));
        if (shop != null) {
            task.setReviewInfo(shop.getName());
        } else {
            log.error("shop is null!");
        }
        return task;
    }

}
