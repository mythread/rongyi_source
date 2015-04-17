package com.rongyi.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.Job;
import com.rongyi.cms.mapper.JobMapper;

/**
 * @author jiejie 2014年4月8日 上午10:40:05
 */
@Repository
public class JobDao {

    @Autowired
    private JobMapper jobMapper;

    public Integer insert(Job job) {
        return jobMapper.insert(job);
    }

    /**
     * 查询未处理的job
     */
    public List<Job> listUnHandleJob() {
        return jobMapper.listUnHandleJob();
    }

    public Job getUnHandleJob(Integer type, String cmsId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cmsId", cmsId);
        map.put("type", type);
        return jobMapper.getUnHandelJob(map);
    }

    public Integer updateJobTime(Integer id) {
        return jobMapper.updateJobTime(id);
    }

    public Integer updateOperateType(Integer id, Integer operateType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("operateType", operateType);
        return jobMapper.updateOperateType(map);
    }

    public Integer batchUpdateJobStatus(List<Integer> jobIds) {
        return jobMapper.batchUpdateJobStatus(jobIds);
    }
}
