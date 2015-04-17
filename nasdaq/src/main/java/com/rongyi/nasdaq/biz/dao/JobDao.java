package com.rongyi.nasdaq.biz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rongyi.nasdaq.biz.domain.JobDO;
import com.rongyi.nasdaq.biz.mapper.JobMapper;

/**
 * 类JobDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月22日 下午4:01:55
 */
@Repository
public class JobDao extends BaseDao<JobDO, JobMapper> {

    public List<JobDO> listPagination(Integer startRecordIndex, Integer pageSize, Integer placeId, Integer typeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobPlaceId", placeId);
        map.put("jobTypeId", typeId);
        map.put("startRecordIndex", startRecordIndex);
        map.put("pageSize", pageSize);
        return e.listPagination(map);
    }

    /**
     * count总数
     */
    public Integer getTotalNum(Integer placeId, Integer typeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobPlaceId", placeId);
        map.put("jobTypeId", typeId);
        return e.getTotalNum(map);
    };
}
