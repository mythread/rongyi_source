package com.rongyi.mina.dao;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 更新任务修改时间
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public int updateGmtModifiedById(Integer id, String mallName) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("mallName", mallName);
        return taskMapper.updateGmtModifiedById(map);
    }

    public CmsTask selectCmsTask(Integer type, String mallId, String cmsOuterId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("mallId", mallId);
        map.put("cmsOuterId", cmsOuterId);
        return taskMapper.selectExistCmsTask(map);
    }
}
