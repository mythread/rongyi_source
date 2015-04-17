package com.mallcms.domain;

import java.util.Date;

public class Shops {
	private String mongo_id; //monogo id
	private String name; //名称
	private String brand_id; //品牌id
	private String description; //描述信息
	private String shop_number; //店铺id
	private String tags; //标签
	private String telephone; //电话
	private String open_time; //营业时间
	private Date updated_at;
	private String address; //地址
	private boolean on_status; //开启关闭 0 关闭 1开启
	private int recommend; //是否推荐
	private String zone_id; //楼层id

	
	public String getZone_id() {
		return zone_id;
	}

	public void setZone_id(String zone_id) {
		this.zone_id = zone_id;
	}

	public boolean isOn_status() {
		return on_status;
	}

	public void setOn_status(boolean on_status) {
		this.on_status = on_status;
	}

	public String getMongo_id() {
		return mongo_id;
	}

	public void setMongo_id(String mongo_id) {
		this.mongo_id = mongo_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShop_number() {
		return shop_number;
	}

	public void setShop_number(String shop_number) {
		this.shop_number = shop_number;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}


	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
}
