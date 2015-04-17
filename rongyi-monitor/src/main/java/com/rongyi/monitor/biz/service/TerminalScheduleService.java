package com.rongyi.monitor.biz.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongyi.monitor.biz.dao.TerminalScheduleInfoDao;
import com.rongyi.monitor.biz.domain.TerminalScheduleInfo;
import com.rongyi.monitor.biz.mongo.dao.impl.MallDaoImpl;

/**
 * 类TerminalScheduleService.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月9日 下午3:37:18
 */
@Service
public class TerminalScheduleService {

    @Autowired
    private TerminalScheduleInfoDao terminalScheduleInfoDao;

    @Autowired
    private MallDaoImpl             mallMongoDao;

    public Integer insert(TerminalScheduleInfo info) {
        terminalScheduleInfoDao.insert(info);
        return info.getId();
    }

    public List<TerminalScheduleInfo> listfor30Min() {
        return terminalScheduleInfoDao.listfor30Min();
    }

    public List<TerminalScheduleInfo> listUnableStatus() {
        return terminalScheduleInfoDao.listUnableStatus();
    }

    public String getMallName(String mallId) {
        if (StringUtils.isEmpty(mallId)) {
            return "";
        }
        return mallMongoDao.getMallName(mallId);
    }

    public boolean updateGmtModified(TerminalScheduleInfo info) {
        return terminalScheduleInfoDao.updateGmtModified(info);
    }
}
