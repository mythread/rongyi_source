package com.rongyi.monitor.biz.domain;

import java.util.Date;

/**
 * 短信跟踪记录
 * 
 * @author jiejie 2014年6月10日 下午3:35:48
 */
public class TraceRecordDO {

    private Integer id;
    private Date    gmtCreate;  // 发送时间
    private Date    gmtModified;
    private String  receiverTel; // 接收人的手机号
    private String  message;    // 发送的内容
    private String  terminalId; // 终端机Id
    private String  mallId;     // 商场ID
    private String  smsStatus;  // 短信状态码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

}
