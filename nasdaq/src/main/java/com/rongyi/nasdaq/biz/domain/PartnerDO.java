package com.rongyi.nasdaq.biz.domain;

import java.util.Date;

/**
 * 合作伙伴
 * 
 * @author jiejie 2014年5月19日 下午7:05:00
 */
public class PartnerDO {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer status;     // 状态：0：可用，1：不可用
    private String  mallLogo;   // 商场logo
    private String  mallName;   // 商场名称
    private String  memo;       // 简介

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMallLogo() {
        return mallLogo;
    }

    public void setMallLogo(String mallLogo) {
        this.mallLogo = mallLogo;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
