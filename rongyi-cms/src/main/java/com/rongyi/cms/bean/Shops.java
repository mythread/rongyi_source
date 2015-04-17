package com.rongyi.cms.bean;

import java.util.Date;

public class Shops extends BaseBean{
    private Integer id;

    private String address;

    private String averageConsumption;

    private Integer brandId;

    private Integer commentCount;

    private String coordinate;

    private String doorCoordinate;

    private String location;

    private String name;

    private Integer oldCode;

    private Integer oldId;

    private Integer rank;

    private String shopNumber;

    private Integer shopType;

    private String slug;

    private String subtitle;

    private String tags;

    private String telephone;

    private String terminalPosition;

    private String terminalShop;

    private Date updatedAt;

    private String zoneId;

    private Byte onStatus;

    private Integer synchStatus;

    private String synchMsg;

    private Integer pid;

    private Integer recordType;

    private String openTime;

    private String mongoId;

    private Integer recommend;

    private String mongoBrandId;

    private String description;
    
    private Brands brands;
    
    private String searchParams;
       
    private String categoriesId;
    
    private Integer synchStatus1;
    
    private Integer synchStatus2;
	    
	private Integer synchStatus3;
    
	private Byte deleteStatus;

    public Integer getSynchStatus1() {
		return synchStatus1;
	}

	public void setSynchStatus1(Integer synchStatus1) {
		this.synchStatus1 = synchStatus1;
	}

	public Integer getSynchStatus2() {
		return synchStatus2;
	}

	public void setSynchStatus2(Integer synchStatus2) {
		this.synchStatus2 = synchStatus2;
	}

	public Integer getSynchStatus3() {
		return synchStatus3;
	}

	public void setSynchStatus3(Integer synchStatus3) {
		this.synchStatus3 = synchStatus3;
	}

	public String getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(String searchParams) {
		this.searchParams = searchParams;
	}
	
	public Brands getBrands() {
		return brands;
	}

	public void setBrands(Brands brands) {
		this.brands = brands;
	}

	public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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
        this.shopNumber = shopNumber == null ? null : shopNumber.trim();
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime == null ? null : openTime.trim();
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId == null ? null : mongoId.trim();
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
        this.mongoBrandId = mongoBrandId == null ? null : mongoBrandId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public String getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(String categoriesId) {
		this.categoriesId = categoriesId;
	}

	public Byte getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Byte deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
    
    
}