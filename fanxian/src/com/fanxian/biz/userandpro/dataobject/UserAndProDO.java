package com.fanxian.biz.userandpro.dataobject;

import java.util.Date;

import com.fanxian.biz.userandpro.cons.AlipayStatusEnum;

/**
 * 用户通过网站购买商品时，记录对应的信息
 */
public class UserAndProDO {

    private Integer id;
    // 该时间结合阿里妈妈后台创建订单的时间，用来区别同一商品被多人购买的情况
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer status;
    private String  identity;    // 淘宝商品的id
    private Integer userId;
    private String  productUrl;  // 淘宝商品的url
    private String  ip;
    private Integer price;        // 返回给用户的佣金，单位: 分

    /**
     * @see AlipayStatusEnum
     */
    private String  alipayStatus; // yes:表示已经充值过了，no：表示未充值过

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAlipayStatus() {
        return alipayStatus;
    }

    public void setAlipayStatus(String alipayStatus) {
        this.alipayStatus = alipayStatus;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
