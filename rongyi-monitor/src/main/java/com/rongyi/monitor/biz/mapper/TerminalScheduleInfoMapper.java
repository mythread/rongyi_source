package com.rongyi.monitor.biz.mapper;

import java.util.List;

import com.rongyi.monitor.biz.domain.TerminalScheduleInfo;

/**
 * 类TerminalScheduleInfoMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月9日 下午1:28:53
 */
public interface TerminalScheduleInfoMapper {

    public Integer insert(TerminalScheduleInfo info);

    /**
     * 更新修改时间
     * 
     * @return
     */
    public Integer updateGmtModified(TerminalScheduleInfo info);

    /**
     * 查询目前时间 - 更新时间 >= 30 min，并且是和当天时间比较
     * 
     * @return
     */
    public List<TerminalScheduleInfo> listfor30Min();

    /**
     * 查询不正常，status=1
     * 
     * @return
     */
    public List<TerminalScheduleInfo> listUnableStatus();

}
