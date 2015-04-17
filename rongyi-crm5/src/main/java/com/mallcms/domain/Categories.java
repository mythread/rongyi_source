package com.mallcms.domain;

import java.util.Date;

public class Categories {
	private String name; //名称
	private String parent_id; //父id 
	private String old_code; 
	private String old_id;
	private int position; //排序
	private Boolean app_show; //app展示
	private Date updated_at; 
	private String id; //id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getOld_code() {
		return old_code;
	}
	public void setOld_code(String old_code) {
		this.old_code = old_code;
	}
	public String getOld_id() {
		return old_id;
	}
	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Boolean getApp_show() {
		return app_show;
	}
	public void setApp_show(Boolean app_show) {
		this.app_show = app_show;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
