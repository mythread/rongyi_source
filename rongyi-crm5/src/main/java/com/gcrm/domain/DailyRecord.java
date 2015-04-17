package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 每天日报记录
 * 
 * @author jiejie 2014年4月22日 下午2:54:02
 */
public class DailyRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1314764725518703831L;
    private Date              dailyDate;                               // 日报时间
    private String            content;                                 // 内容

    public Date getDailyDate() {
        return dailyDate;
    }

    public void setDailyDate(Date dailyDate) {
        this.dailyDate = dailyDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getName() {
        return "DailyRecord";
    }

}
