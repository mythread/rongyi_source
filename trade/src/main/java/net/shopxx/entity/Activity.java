package net.shopxx.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.shopxx.bean.ProductImage;

/**
 * 活动
 * @author jiejie 2014年3月5日 下午4:09:58
 */
@Entity
@Table(name = "ry_activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = -8801903459622034987L;

    // 活动状态(待发布,未开始，进行中，结束)
    public enum ActivityStatusEnum {
        WAIT_RELEASE, NOT_START, ONGOING, END
    }

    private Integer            id;            // ID
    private Date               createDate;    // 创建日期
    private Date               modifyDate;    // 修改日期
    private Integer            status;        // 状态 0，可用，1，不可用
    private Date               startDate;     // 开始时间
    private Date               endDate;       // 结束时间
    private ActivityStatusEnum activityStatus; // 活动状态

    private String             pic;           // 活动图片
    private Integer            buyerId;       // 买手ID
    private String             mallId;        // 商场ID
    private String             title;         // 活动标题
    private String             content;       // 活动内容
    private String             description;   // 活动描述

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    public ActivityStatusEnum getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatusEnum activityStatus) {
        this.activityStatus = activityStatus;
    }

    @Transient
    public void setActivityPic(List<ProductImage> productImageList) {
        if (productImageList == null || productImageList.size() == 0) {
            pic = null;
            return;
        }
        JSONArray jsonArray = JSONArray.fromObject(productImageList);
        pic = jsonArray.toString();
    }

    /**
     * 活动是否是待发布状态
     * @return
     */
    @Transient
    public boolean isWaitRelease(){
        return activityStatus == ActivityStatusEnum.WAIT_RELEASE;
    }
}
