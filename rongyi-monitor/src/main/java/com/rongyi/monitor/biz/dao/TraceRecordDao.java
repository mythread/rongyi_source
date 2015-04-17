package com.rongyi.monitor.biz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.monitor.biz.domain.TraceRecordDO;
import com.rongyi.monitor.biz.mapper.TraceRecordMapper;

/**
 * 类TraceRecordDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月10日 下午3:52:03
 */
@Repository
public class TraceRecordDao {

    @Autowired
    private TraceRecordMapper traceRecordMapper;

    public Integer insert(TraceRecordDO recordDO) {
        return traceRecordMapper.insert(recordDO);
    }
}
