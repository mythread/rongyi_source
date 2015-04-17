package net.shopxx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shopxx.dao.ActivityDao;
import net.shopxx.dao.AdminDao;
import net.shopxx.entity.Activity;
import net.shopxx.service.ActivityService;

@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity, Integer> implements ActivityService {

    @Resource
    private ActivityDao activityDao;

    @Resource
    public void setBaseDao(ActivityDao activityDao) {
        super.setBaseDao(activityDao);
    }

}
