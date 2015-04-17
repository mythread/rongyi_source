package com.mallcms.domain;

import java.io.Serializable;
import java.util.Date;

public class PhotosPojo implements Serializable {

    private static final long serialVersionUID = 7234438771237091892L;

    private Integer           id;

    private Integer           status;                                 // 0:可用；1：不可用

    private Date              createdAt;

    private String            file;

    private Integer           ownerId;

    private String            ownerType;

    private Integer           position;

    private Date              updatedAt;

    private String            ownerMongoId;

    private String            fileUrl;

    private String            mongoId;

    private String            mallId;

    private Integer           cmsPhotoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType == null ? null : ownerType.trim();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwnerMongoId() {
        return ownerMongoId;
    }

    public void setOwnerMongoId(String ownerMongoId) {
        this.ownerMongoId = ownerMongoId == null ? null : ownerMongoId.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId == null ? null : mongoId.trim();
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public Integer getCmsPhotoId() {
        return cmsPhotoId;
    }

    public void setCmsPhotoId(Integer cmsPhotoId) {
        this.cmsPhotoId = cmsPhotoId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
