package com.mallcms.domain;

import java.util.Date;

public class Advertisements {
	private String name; //名称
	private String url;//详情跳转页店铺id
	private Date start_time;//开始时间
	private Date end_time;//结束时间
	private String picture;//图片名称
	private String minPicture;//图片名称
	private Date updated_at;//更新时间
	private Date created_at;//创建时间
	private String mongo_id;//mongo数据库id
	private String mall_id;//商城id
	private String ad_zones_id;//广告位id
	private boolean on_status;//开启关闭状态 0 关闭 1开启
	private boolean default_pricture;//默认图片

	public boolean isDefault_pricture() {
		return default_pricture;
	}

	public String getAd_zones_id() {
		return ad_zones_id;
	}

	public void setAd_zones_id(String ad_zones_id) {
		this.ad_zones_id = ad_zones_id;
	}

	public void setDefault_pricture(boolean default_pricture) {
		this.default_pricture = default_pricture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}


	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}


	public String getMongo_id() {
		return mongo_id;
	}

	public void setMongo_id(String mongo_id) {
		this.mongo_id = mongo_id;
	}

	public String getMall_id() {
		return mall_id;
	}

	public void setMall_id(String mall_id) {
		this.mall_id = mall_id;
	}

	public boolean isOn_status() {
		return on_status;
	}

	public void setOn_status(boolean on_status) {
		this.on_status = on_status;
	}

	public String getMinPicture() {
		return minPicture;
	}

	public void setMinPicture(String minPicture) {
		this.minPicture = minPicture;
	}

}
