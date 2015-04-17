package com.rongyi.monitor.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongyi.monitor.biz.dao.TraceRecordDao;
import com.rongyi.monitor.biz.domain.TraceRecordDO;

/**
 * 类TraceRecordService.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月10日 下午3:55:35
 */
@Service
public class TraceRecordService {

    @Autowired
    private TraceRecordDao recordDao;

    public Integer insert(TraceRecordDO recordDO) {
        if (recordDO == null) {
            return null;
        }
        return recordDao.insert(recordDO);
    }
}
