package com.rongyi.cms.bean;

import java.util.Date;

public class AdZones {
    private String id;

    private String name;

    private String description;

    private Date updatedAt;

    private Date createdAt;

    private String zoneOwnerId;

    private Byte ownerType;

    private Integer seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public String getZoneOwnerId() {
        return zoneOwnerId;
    }

    public void setZoneOwnerId(String zoneOwnerId) {
        this.zoneOwnerId = zoneOwnerId == null ? null : zoneOwnerId.trim();
    }

    public Byte getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Byte ownerType) {
        this.ownerType = ownerType;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}