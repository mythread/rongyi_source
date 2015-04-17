package com.rongyi.nasdaq.biz.domain;

import java.util.Date;

/**
 * 职位类别
 * 
 * @author jiejie 2014年5月20日 下午1:45:04
 */
public class JobTypeDO {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer status;     // 状态：0：可用，1：不可用
    private String  name;       // 职位类别

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
