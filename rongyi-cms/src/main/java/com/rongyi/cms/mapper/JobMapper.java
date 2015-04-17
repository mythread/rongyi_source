package com.rongyi.cms.mapper;

import java.util.List;
import java.util.Map;

import com.rongyi.cms.bean.Job;

public interface JobMapper {

    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Job record);

    Integer insertSelective(Job record);

    Job selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Job record);

    Integer updateByPrimaryKey(Job record);

    /**
     * 查询未处理的job
     */
    List<Job> listUnHandleJob();

    Job getUnHandelJob(Map<String, Object> map);

    Integer updateJobTime(Integer id);

    Integer updateOperateType(Map<String, Object> map);

    Integer batchUpdateJobStatus(List<Integer> jobIds);
}
