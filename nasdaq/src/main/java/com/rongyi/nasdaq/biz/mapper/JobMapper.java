package com.rongyi.nasdaq.biz.mapper;

import java.util.Map;

import com.rongyi.nasdaq.biz.domain.JobDO;

/**
 * 类JobMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月21日 下午3:43:43
 */
public interface JobMapper extends BaseMapper<JobDO> {

    /**
     * count总数
     */
    public Integer getTotalNum(Map<String, Object> map);
}
