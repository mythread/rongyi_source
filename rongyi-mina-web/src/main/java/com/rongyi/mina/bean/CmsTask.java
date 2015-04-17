package com.rongyi.mina.bean;

import java.util.Date;

/**
 * @author jiejie 2014年4月3日 下午5:32:17
 */
public class CmsTask {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer taskStatus;  // 0:新任务 1：已处理任务
    private Integer type;        // 商铺、广告、品牌
    private String  brandUrl;    // 品牌图片连接
    private String  mallName;    // 商铺名
    private String  reviewInfo;  // 审核信息
    private Date    gmtJobCreate; // job的创建时间
    private Integer actionType;  // 0:新增 1：更新
    private Integer reviewStatus; // 审核状态
    private String  showInfo;    // 驳回理由
    private String  mallId;      // 商场ID
    private String  cmsOuterId;

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

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBrandUrl() {
        return brandUrl;
    }

    public void setBrandUrl(String brandUrl) {
        this.brandUrl = brandUrl;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public Date getGmtJobCreate() {
        return gmtJobCreate;
    }

    public void setGmtJobCreate(Date gmtJobCreate) {
        this.gmtJobCreate = gmtJobCreate;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(String reviewInfo) {
        this.reviewInfo = reviewInfo;
    }

    public String getCmsOuterId() {
        return cmsOuterId;
    }

    public void setCmsOuterId(String cmsOuterId) {
        this.cmsOuterId = cmsOuterId;
    }

}
