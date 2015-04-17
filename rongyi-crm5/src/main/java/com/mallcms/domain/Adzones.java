package com.mallcms.domain;

import java.util.Date;

public class Adzones {
	private String id; //id
	private String name;//名称
	private String zone_owner_type;//属于类型
	private String zone_owner_id;//属于商城id
	private String description;//描述
	private String advertisement_ids;//详情信息
	private Date updated_at;
	private Date created_at;
	private String position;//排序
	private Boolean owner_type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZone_owner_type() {
		return zone_owner_type;
	}

	public void setZone_owner_type(String zone_owner_type) {
		this.zone_owner_type = zone_owner_type;
	}

	public String getZone_owner_id() {
		return zone_owner_id;
	}

	public void setZone_owner_id(String zone_owner_id) {
		this.zone_owner_id = zone_owner_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdvertisement_ids() {
		return advertisement_ids;
	}

	public void setAdvertisement_ids(String advertisement_ids) {
		this.advertisement_ids = advertisement_ids;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(Boolean owner_type) {
		this.owner_type = owner_type;
	}

}
