package com.rongyi.monitor.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongyi.monitor.biz.domain.TerminalScheduleInfo;
import com.rongyi.monitor.biz.domain.TraceRecordDO;
import com.rongyi.monitor.biz.service.TerminalScheduleService;
import com.rongyi.monitor.biz.service.TraceRecordService;
import com.rongyi.monitor.cache.TerminalCacheManager;
import com.rongyi.monitor.common.Constant;
import com.rongyi.monitor.common.MessageManger;
import com.rongyi.monitor.common.PropertyConfigurer;

/**
 * 终端机定时job类
 * 
 * @author jiejie 2014年6月9日 下午10:04:41
 */
public class TerminalJob {

    private static final Logger     LOG        = LoggerFactory.getLogger(TerminalJob.class);

    @Autowired
    private TerminalScheduleService service;
    @Autowired
    private PropertyConfigurer      propertyConfigurer;
    @Autowired
    private TraceRecordService      traceRecordService;

    private String[]                telArray;

    private int                     startHour  = 9;                                          // 开始时间的小时
    private int                     endHour    = 22;                                         // 结束时间的小时
    private int                     endMinute  = 25;                                         // 结束时间的分钟
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public void init() {
        String tels = (String) propertyConfigurer.getProperty(Constant.TERMINAL_TEL);
        telArray = tels.split(",");
    }

    public void work() {
        try {
            if (isWork()) {
                List<TerminalScheduleInfo> badTermials = getBadTerminalInfo();
                if (badTermials != null && badTermials.size() > 0) {
                    String msg = getSmsMessage(badTermials);
                    for (String str : telArray) {
                        if (TerminalCacheManager.isExpired(str)) {
                            String smsStatus = sendMessage(str, msg);
                            TerminalCacheManager.setTerminalCache(str, System.currentTimeMillis());
                            insertTraceRecord(str, msg, badTermials, smsStatus);
                        }
                    }
                }
            } else {

                TerminalCacheManager.cleanAll();
            }
        } catch (Exception e) {
            LOG.error("jobtask throw exception:{}", e.getMessage(), e);
        }
    }

    /**
     * 获取有问题的终端机信息
     */
    private List<TerminalScheduleInfo> getBadTerminalInfo() {
        List<TerminalScheduleInfo> result = new ArrayList<TerminalScheduleInfo>();
        result.addAll(service.listfor30Min());
        result.addAll(service.listUnableStatus());
        return result;
    }

    /**
     * 调用发短信api
     */
    private String sendMessage(String telphone, String msg) {
        return MessageManger.getInstance().sendSmsMessage(telphone, msg);
    }

    private String getSmsMessage(List<TerminalScheduleInfo> badTermials) {
        StringBuilder sb = new StringBuilder(400);
        for (int i = 0; i < badTermials.size(); i++) {
            sb.append("商场【").append(service.getMallName(badTermials.get(i).getMallId())).append("】，其ID【");
            sb.append(badTermials.get(i).getMallId()).append("】，");
            sb.append("终端机编号【").append(badTermials.get(i).getTerminalId()).append("】，");
            sb.append(dateFormat.format(badTermials.get(i).getGmtModified()));
            if (i < badTermials.size() - 1) {
                sb.append(";");
            }
        }
        sb.append(" 出现故障！");
        return sb.toString();
    }

    /**
     * 数据库插入跟踪记录
     * 
     * @param phone
     * @param smsMsg
     * @param badTermials
     */
    private void insertTraceRecord(String phone, String smsMsg, List<TerminalScheduleInfo> badTermials, String smsStatus) {
        for (TerminalScheduleInfo t : badTermials) {
            TraceRecordDO traceRecord = new TraceRecordDO();
            traceRecord.setMallId(t.getMallId());
            traceRecord.setMessage(smsMsg);
            traceRecord.setReceiverTel(phone);
            traceRecord.setTerminalId(t.getTerminalId());
            traceRecord.setSmsStatus(smsStatus);
            traceRecordService.insert(traceRecord);
        }
    }

    /**
     * 判断job是否需要发短信，查数据库
     */
    public boolean isWork() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        if (hour >= startHour && hour <= endHour) {
            if (hour == endHour) {
                if (minute > endMinute) {
                    return false;
                }
                return true;
            }
            return true;
        }
        return false;
    }
}
