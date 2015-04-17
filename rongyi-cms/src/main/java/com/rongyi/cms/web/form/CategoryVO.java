package com.rongyi.cms.web.form;

import java.util.List;

public class CategoryVO {
	private String id;
	private String name;
	private List<CategoryVO> list;
	
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
	public List<CategoryVO> getList() {
		return list;
	}
	public void setList(List<CategoryVO> list) {
		this.list = list;
	}
	
	
}
