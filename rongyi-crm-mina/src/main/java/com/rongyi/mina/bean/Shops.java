package com.rongyi.mina.bean;

import java.util.Date;

public class Shops {

    private Integer  id;

    private String  address;

    private String  averageConsumption;

    private String  brandId;

    private Integer commentCount;

    private String  coordinate;

    private String  description;

    private String  doorCoordinate;

    private String  location;

    private String  name;

    private Integer oldCode;

    private Integer oldId;

    private Integer rank;

    private String  shopNumber;

    private Integer shopType;

    private String  slug;

    private String  subtitle;

    private String  tags;

    private String  telephone;

    private String  terminalPosition;

    private String  terminalShop;

    private Date    updatedAt;

    private String  zoneId;

    private Byte    onStatus;

    private Integer synchStatus;

    private String  synchMsg;

    private String  pid;

    private Integer recordType;

    private String  openTime;

    private String  mongoId;

    private Integer recommend;

    private String  mongoBrandId;

    private Integer taskId;

    private String  cmsOuterId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id == null ? 0 : id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(String averageConsumption) {
        this.averageConsumption = averageConsumption == null ? null : averageConsumption.trim();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate == null ? null : coordinate.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDoorCoordinate() {
        return doorCoordinate;
    }

    public void setDoorCoordinate(String doorCoordinate) {
        this.doorCoordinate = doorCoordinate == null ? null : doorCoordinate.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOldCode() {
        return oldCode;
    }

    public void setOldCode(Integer oldCode) {
        this.oldCode = oldCode;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug == null ? null : slug.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getTerminalPosition() {
        return terminalPosition;
    }

    public void setTerminalPosition(String terminalPosition) {
        this.terminalPosition = terminalPosition == null ? null : terminalPosition.trim();
    }

    public String getTerminalShop() {
        return terminalShop;
    }

    public void setTerminalShop(String terminalShop) {
        this.terminalShop = terminalShop == null ? null : terminalShop.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId == null ? null : zoneId.trim();
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

    public String getSynchMsg() {
        return synchMsg;
    }

    public void setSynchMsg(String synchMsg) {
        this.synchMsg = synchMsg == null ? null : synchMsg.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
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

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getMongoBrandId() {
        return mongoBrandId;
    }

    public void setMongoBrandId(String mongoBrandId) {
        this.mongoBrandId = mongoBrandId;
    }
}
