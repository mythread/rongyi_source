package com.rongyi.mina.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * crm端的job记录
 * 
 * @author jiejie 2014年4月9日 下午2:24:46
 */
public class CrmJob implements Serializable {

    private static final long serialVersionUID = -4798143786451574411L;
    private Integer           id;
    private Date              gmtCreate;
    private Date              gmtModified;
    private Integer           type;                                    // 类型：商铺、广告、品牌
    private Integer           jobStatus;
    private String            mallId;                                  // 商场id
    private String            cmsOuterId;                              // cms系统中的外键ID
    private String            mongodbId;
    private Integer           reviewStatus;                            // 审核状态
    private String            memo;
    private String            picUrl;                                  // 图片url
    private String            minPicUrl;                               // 小图url(缩略图)

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getCmsOuterId() {
        return cmsOuterId;
    }

    public void setCmsOuterId(String cmsOuterId) {
        this.cmsOuterId = cmsOuterId;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMongodbId() {
        return mongodbId;
    }

    public void setMongodbId(String mongodbId) {
        this.mongodbId = mongodbId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMinPicUrl() {
        return minPicUrl;
    }

    public void setMinPicUrl(String minPicUrl) {
        this.minPicUrl = minPicUrl;
    }

}
