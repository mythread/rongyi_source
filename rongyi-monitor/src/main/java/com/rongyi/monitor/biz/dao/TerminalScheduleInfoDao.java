package com.rongyi.monitor.biz.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.monitor.biz.domain.TerminalScheduleInfo;
import com.rongyi.monitor.biz.mapper.TerminalScheduleInfoMapper;

/**
 * 类TerminalScheduleInfoDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月9日 下午2:12:17
 */
@Repository
public class TerminalScheduleInfoDao {

    @Autowired
    private TerminalScheduleInfoMapper terminalScheduleInfoMapper;

    public Integer insert(TerminalScheduleInfo info) {
        return terminalScheduleInfoMapper.insert(info);
    }

    public boolean updateGmtModified(TerminalScheduleInfo info) {
        return terminalScheduleInfoMapper.updateGmtModified(info) > 0;
    }

    public List<TerminalScheduleInfo> listUnableStatus() {
        return terminalScheduleInfoMapper.listUnableStatus();
    }

    public List<TerminalScheduleInfo> listfor30Min() {
        return terminalScheduleInfoMapper.listfor30Min();
    }

}
