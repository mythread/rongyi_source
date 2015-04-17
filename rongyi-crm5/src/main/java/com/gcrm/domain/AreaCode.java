package com.gcrm.domain;

import java.io.Serializable;

/**
 * 地域信息
 * @author jianbo.feng
 * @company hintsoft.com.cn
 * @create 2013/12/16
 */
public class AreaCode extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856497869130624069L;

	/**地域代码*/
	private String areaCode;
	
	/**地域名称*/
	private String areaName;
	
	/**上级地域代码*/
	private String superiorAreaCode;
	
	@Override
	public AreaCode clone(){
		AreaCode o = null;
		try{
			o = (AreaCode)super.clone();
		}catch(CloneNotSupportedException e){
			   e.printStackTrace();
		}
		return o;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getSuperiorAreaCode() {
		return superiorAreaCode;
	}

	public void setSuperiorAreaCode(String superiorAreaCode) {
		this.superiorAreaCode = superiorAreaCode;
	}

	@Override
	public String getName() {
		return this.areaName;
	}


}
