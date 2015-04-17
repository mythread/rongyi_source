package com.rongyi.cms.bean;

import java.util.Date;

public class Brands {
    private Integer id;

    private String address;

    private String averageConsumption;

    private String aliases;

    private String cname;

    private String ename;

    private String icon;

    private String name;

    private String operator;

    private String tags;

    private String telephone;

    private Date updatedAt;

    private Integer oldCode;

    private Integer oldId;

    private Integer syhchStatus;

    private String syhchMsg;

    private Integer pid;

    private Integer recordType;

    private String mongoId;

    private String description;
    
    private String iconUrl;
    
    private Categories categories;
    
    

    public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
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

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases == null ? null : aliases.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public Integer getSyhchStatus() {
        return syhchStatus;
    }

    public void setSyhchStatus(Integer syhchStatus) {
        this.syhchStatus = syhchStatus;
    }

    public String getSyhchMsg() {
        return syhchMsg;
    }

    public void setSyhchMsg(String syhchMsg) {
        this.syhchMsg = syhchMsg == null ? null : syhchMsg.trim();
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

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId == null ? null : mongoId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl== null ? null : iconUrl.trim();
	}
    
    
}