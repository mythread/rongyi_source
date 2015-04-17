package com.gcrm.vo;

/**
 * 跟踪记录统计
 */
public class StatisticsRecordVO {

    private String  assignToName; // 跟进人
    private String  intentName;   // 客户意向
    private String  visitName;    // 拜访情况
    private String  createDate;    // 创建时间
    private String  assignDate;   // 跟进时间
    private Integer days;         // 跟踪天数
    private long phoneVisitNum; // 电话拜访次数
    private long dropVisitNum; // 上门拜访次数

    public String getAssignToName() {
        return assignToName;
    }

    public void setAssignToName(String assignToName) {
        this.assignToName = assignToName;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public long getPhoneVisitNum() {
        return phoneVisitNum;
    }

    public void setPhoneVisitNum(long phoneVisitNum) {
        this.phoneVisitNum = phoneVisitNum;
    }

    public long getDropVisitNum() {
        return dropVisitNum;
    }

    public void setDropVisitNum(long dropVisitNum) {
        this.dropVisitNum = dropVisitNum;
    }

    
    public String getCreateDate() {
        return createDate;
    }

    
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    
    public long getTotalVisitNum(){
        return phoneVisitNum + dropVisitNum;
    }
}
