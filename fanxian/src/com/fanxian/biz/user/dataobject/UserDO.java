package com.fanxian.biz.user.dataobject;

import java.util.Date;

/**
 * 用户表，记录支付宝帐号的，在这里将userId,写入cookie中
 * 
 * @author jiejie 2014-1-24 下午5:05:42
 */
public class UserDO {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer status;
    private String  account;    // 用户帐号
    private String  cookieId;   // 记录用户第一次访问网站时的cookieId
    private String  memo;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
