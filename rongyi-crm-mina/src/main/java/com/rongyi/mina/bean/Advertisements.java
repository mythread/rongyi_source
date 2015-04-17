package com.rongyi.mina.bean;

import java.util.Date;

public class Advertisements {

    private Integer id;

    private String  name;

    private String  shopUrl;

    private Date    startTime;

    private Date    endTime;

    private String  picture;

    private Date    updatedAt;

    private Date    createdAt;

    private String  adZoneId;

    private Byte    deleteStatus;

    private Byte    onStatus;

    private Integer synchStatus;

    private String  mongodbId;

    private String  synchMsg;

    private Integer pid;

    private Byte    recordType;

    private String  minPicture;

    private Integer defaultPicture;

    private String  pictureUrl;

    private String  minPictureUrl;

    private Integer taskId;        // 任务ID
    private String  cmsOuterId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl == null ? null : shopUrl.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAdZoneId() {
        return adZoneId;
    }

    public void setAdZoneId(String adZoneId) {
        this.adZoneId = adZoneId == null ? null : adZoneId.trim();
    }

    public Byte getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Byte deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Byte getOnStatus() {
        return onStatus;
    }

    public void setOnStatus(Byte onStatus) {
        this.onStatus = onStatus;
    }

    public Integer getSynchStatus() {
        return synchStatus;
    }

    public void setSynchStatus(Integer synchStatus) {
        this.synchStatus = synchStatus;
    }

    public String getMongodbId() {
        return mongodbId;
    }

    public void setMongodbId(String mongodbId) {
        this.mongodbId = mongodbId == null ? null : mongodbId.trim();
    }

    public String getSynchMsg() {
        return synchMsg;
    }

    public void setSynchMsg(String synchMsg) {
        this.synchMsg = synchMsg == null ? null : synchMsg.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Byte getRecordType() {
        return recordType;
    }

    public void setRecordType(Byte recordType) {
        this.recordType = recordType;
    }

    public String getMinPicture() {
        return minPicture;
    }

    public void setMinPicture(String minPicture) {
        this.minPicture = minPicture == null ? null : minPicture.trim();
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getCmsOuterId() {
        return cmsOuterId;
    }

    public void setCmsOuterId(String cmsOuterId) {
        this.cmsOuterId = cmsOuterId;
    }

    public Integer getDefaultPicture() {
        return defaultPicture;
    }

    public void setDefaultPicture(Integer defaultPicture) {
        this.defaultPicture = defaultPicture;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getMinPictureUrl() {
        return minPictureUrl;
    }

    public void setMinPictureUrl(String minPictureUrl) {
        this.minPictureUrl = minPictureUrl;
    }
}
