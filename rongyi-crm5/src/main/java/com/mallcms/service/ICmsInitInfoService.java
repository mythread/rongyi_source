package com.mallcms.service;

public interface ICmsInitInfoService {

	public String initMallInfo(String mallId, String ip, String dbUser,
			String dbPassword);

	public String initAdzonesInfo(String mallId, String ip, String dbUser,
			String dbPassword);

	public String initAdvertisementsInfo(String mallId, String ip,
			String dbUser, String dbPassword);

	public String initShopsInfo(String mallId, String ip, String dbUser,
			String dbPassword);

	public String initBrandsInfo(String mallId, String ip, String dbUser,
			String dbPassword);

	public String initCategoriesInfo(String mallId, String ip, String dbUser,
			String dbPassword);
	
	public String initPhotosInfo(String mallId, String ip, String dbUser,
			String dbPassword);

}
