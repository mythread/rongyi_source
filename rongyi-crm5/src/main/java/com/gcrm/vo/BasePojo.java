package com.gcrm.vo;

import java.io.Serializable;


public class BasePojo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1722590615219759709L;
    private Integer id;
    private String ownerName;
    private String createdByName;
    private String updatedByName;
    private String createdOnName;
    private String updatedOnName;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getCreatedByName() {
        return createdByName;
    }
    
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
    
    public String getUpdatedByName() {
        return updatedByName;
    }
    
    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }
    
    public String getCreatedOnName() {
        return createdOnName;
    }
    
    public void setCreatedOnName(String createdOnName) {
        this.createdOnName = createdOnName;
    }
    
    public String getUpdatedOnName() {
        return updatedOnName;
    }
    
    public void setUpdatedOnName(String updatedOnName) {
        this.updatedOnName = updatedOnName;
    }
    

}
