package com.rongyi.mina.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.CmsTask;
import com.rongyi.mina.mapper.CmsTaskMapper;

/**
 * 类CmsTaskDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月4日 下午2:32:39
 */

@Repository
public class CmsTaskDao {

    @Autowired
    private CmsTaskMapper taskMapper;

    public Integer insert(CmsTask cmsTask) {
        taskMapper.insert(cmsTask);
        return cmsTask.getId();
    }
}
