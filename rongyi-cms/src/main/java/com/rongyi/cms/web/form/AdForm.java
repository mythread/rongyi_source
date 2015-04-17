package com.rongyi.cms.web.form;

/**
 * 
 * @author rongyi-13
 *
 */
public class AdForm {
	private Integer id;

	private String adName;

	private String shopUrl;

	private String startTime;

	private String endTime;

	private String picture;

	private Integer pid;

	private String minPicture;
	/**
	 * 将广告图缩略1
	       使用其他图片2
	 */
	private String hasMinPic;
	
	/**
	 * 无链接0
		商家页面1
	 */
	private String hasLink;
	private String adZoneId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getMinPicture() {
		return minPicture;
	}

	public void setMinPicture(String minPicture) {
		this.minPicture = minPicture;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getHasMinPic() {
		return hasMinPic;
	}

	public void setHasMinPic(String hasMinPic) {
		this.hasMinPic = hasMinPic;
	}

	public String getHasLink() {
		return hasLink;
	}

	public void setHasLink(String hasLink) {
		this.hasLink = hasLink;
	}

	public String getAdZoneId() {
		return adZoneId;
	}

	public void setAdZoneId(String adZoneId) {
		this.adZoneId = adZoneId;
	}


}
