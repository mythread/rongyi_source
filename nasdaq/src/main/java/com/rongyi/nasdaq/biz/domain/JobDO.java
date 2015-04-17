package com.rongyi.nasdaq.biz.domain;

import java.util.Date;

/**
 * 人才招聘
 * 
 * @author jiejie 2014年5月19日 下午7:13:41
 */
public class JobDO {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer status;      // 状态：0：可用，1：不可用
    private String  name;        // 职位名称
    private Integer jobNum;      // 招聘人数
    private Integer jobTypeId;   // 职位类别ID
    private String  jobTypeName;
    private Integer jobPlaceId;  // 工作地点id
    private String  jobPlaceName; // 工作地点名字
    private Integer level;       // 急招，级别 0:一般 1：急招
    private String  memo;        // 职位描述
    private String  email;       // 发送hr邮箱地址
    private Integer depId;       // 部门
    private String  depName;

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

    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
    }

    public Integer getJobPlaceId() {
        return jobPlaceId;
    }

    public void setJobPlaceId(Integer jobPlaceId) {
        this.jobPlaceId = jobPlaceId;
    }

    public String getJobPlaceName() {
        return jobPlaceName;
    }

    public void setJobPlaceName(String jobPlaceName) {
        this.jobPlaceName = jobPlaceName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(Integer jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

}
