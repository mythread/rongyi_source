package com.rongyi.monitor.biz.domain;

import java.util.Date;

/**
 * 终端调度信息
 * 
 * @author jiejie 2014年6月9日 上午11:52:04
 */
public class TerminalScheduleInfo {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private String  terminalId; // 终端机Id
    private Integer status;     // 终端机状态 0：正常运行 1：不运行
    private String  mallId;     // 商场ID
    private String  message;

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

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

}
