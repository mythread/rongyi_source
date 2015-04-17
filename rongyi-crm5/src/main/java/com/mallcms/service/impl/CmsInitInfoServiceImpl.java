package com.mallcms.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

import com.mallcms.domain.Advertisements;
import com.mallcms.domain.Adzones;
import com.mallcms.domain.Brands;
import com.mallcms.domain.Categories;
import com.mallcms.domain.Floor;
import com.mallcms.domain.Mall;
import com.mallcms.domain.Photo;
import com.mallcms.domain.Shops;
import com.mallcms.service.IAdvertisementsService;
import com.mallcms.service.IAdzonesService;
import com.mallcms.service.IBrandsService;
import com.mallcms.service.ICategoriesService;
import com.mallcms.service.ICmsInitInfoService;
import com.mallcms.service.IMallService;
import com.mallcms.service.IPhotosService;
import com.mallcms.service.IShopsService;
import com.mongodb.BasicDBObject;

public class CmsInitInfoServiceImpl implements ICmsInitInfoService {
	private final static Logger logger = Logger
			.getLogger(CmsInitInfoServiceImpl.class);
	private ICategoriesService categoriesServiceImpl;
	private IBrandsService brandsServiceImpl;
	private IShopsService shopsServiceImpl;
	private IAdzonesService adzonesServiceImpl;
	private IAdvertisementsService advertisementsServiceImpl;
	private IMallService mallServiceImpl;
	private IPhotosService photosServiceImpl;

	/**
	 * 同步商城信息
	 */
	@Override
	public String initMallInfo(String mallId, String ip, String dbUser,
			String dbPassword) {
		Mall mall = mallServiceImpl.getMallById(mallId);
		String tag = "1";
		Connection conn = null;
		try {
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "INSERT INTO param(service_type,value,biz_type,seq,remark) VALUES(?,?,?,?,?)";
			QueryRunner runner = new QueryRunner();

			if (runner.update(conn, sql, mall.getId(), mall.getName(), "1",
					"1", "商城基本信息") > 0) {
				tag = "0";
			}
			List<Floor> floorList=mallServiceImpl.getFloorByMallId(mallId);
			for (Floor floor : floorList) {
				if (runner.update(conn, sql, floor.getId(), floor.getName(), "2",
						"2", "楼层名称") > 0) {
					tag = "0";
				}
			}
				
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("同步商城信息出错"+e.getMessage());
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 同步 广告位信息
	 */
	@Override
	public String initAdzonesInfo(String mallId, String ip, String dbUser,
			String dbPassword) {

		List<Adzones> adList = adzonesServiceImpl.getAdzonesByMallId(mallId);
		String tag = "1";
		Connection conn = null;
		try {
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "INSERT INTO ad_zones(id,name,description,updated_at,created_at,zone_owner_id,owner_type,seq) VALUES(?,?,?,?,?,?,?,?)";
			int i = 1;
			QueryRunner runner = new QueryRunner();
			for (Adzones adzones : adList) {
				if (runner.update(conn, sql, adzones.getId(),
						adzones.getName(), adzones.getDescription(),
						adzones.getUpdated_at(), adzones.getCreated_at(),
						adzones.getZone_owner_id(), adzones.getOwner_type(), i) > 0) {
					tag = "0";
				}
				i++;
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("同步 广告位信息出错"+e.getMessage());
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 同步广告详细信息
	 */
	@Override
	public String initAdvertisementsInfo(String mallId, String ip,
			String dbUser, String dbPassword) {

		List<Advertisements> adverList = advertisementsServiceImpl
				.getAdvertisementsByMallId(mallId);
		String tag = "1";
		Connection conn = null;
		String url = "http://rongyi.b0.upaiyun.com/system/advertisement/picture/";
		try {
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "INSERT INTO advertisements(name,shop_url,start_time,end_time,picture,updated_at,created_at,ad_zone_id,delete_status,on_status,synch_status,mongodb_id,record_type,min_picture,picture_url,min_picture_url) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			QueryRunner runner = new QueryRunner();
			for (Advertisements adver : adverList) {
				if (runner.update(conn, sql, adver.getName(), adver.getUrl(),
						adver.getStart_time(), adver.getEnd_time(),
						adver.getPicture(), adver.getUpdated_at(),
						adver.getCreated_at(), adver.getAd_zones_id(), false,
						true, 2, adver.getMongo_id(), false, null,
						new StringBuilder(url).append(adver.getMongo_id())
								.append("/").append(adver.getPicture())
								.toString(), -1) > 0) {
					tag = "0";
				}
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("同步广告详细信息出错"+e.getMessage());
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 同步商铺
	 */
	@Override
	public String initShopsInfo(String mallId, String ip, String dbUser,
			String dbPassword) {
		List<Shops> shopsList = shopsServiceImpl.getShopsByMallId(mallId);
		String tag = "1";
		Connection conn = null;
		try {
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "INSERT INTO shops(mongo_id,name,address,shop_number,tags,updated_at,telephone,description,brand_id,open_time,on_status,synch_status,record_type,recommend,mongo_brand_id,zone_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			QueryRunner runner = new QueryRunner();
			for (Shops shop : shopsList) {
				int cms_brandId = getBrandsIdByMongoId(shop.getBrand_id(), ip,
						dbUser, dbPassword);
				if (runner.update(conn, sql, shop.getMongo_id(),
						shop.getName(), shop.getAddress(),
						shop.getShop_number(), shop.getTags(),
						shop.getUpdated_at(), shop.getTelephone(),
						shop.getDescription(), cms_brandId, "08:00 ~ 22:00",
						true, 2, 0, shop.getRecommend(), shop.getBrand_id(),shop.getZone_id()) > 0) {
					tag = "0";
				}
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("同步同步商城出错"+e.getMessage());
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 同步品牌
	 */
	@Override
	public String initBrandsInfo(String mallId, String ip, String dbUser,
			String dbPassword) {
		List<Brands> brandsList = brandsServiceImpl.getBrandsByMallId(mallId);
		String tag = "1";
		String icon_url = "http://rongyi.b0.upaiyun.com/system/brand/icon/";
		Connection conn = null;
		try {
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String cate_sql = "INSERT INTO brands(mongo_id,name,updated_at,cname,ename,description,icon,tags,telephone,syhch_status,pid,record_type,icon_url) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			String link_sql = "INSERT INTO cate_link_brands(categories_id,brands_id) VALUES(?,?)";

			QueryRunner runner = new QueryRunner();
			for (Brands brands : brandsList) {
				if (runner.update(conn, cate_sql, brands.getMongo_id(),
						brands.getName(), brands.getUpdated_at(),
						brands.getCname(), brands.getEname(),
						brands.getDescription(), brands.getIcon(),
						brands.getTag(), brands.getTelephone(), 2, -1, 0,
						new StringBuilder(icon_url)
								.append(brands.getMongo_id()).append("/")
								.append(brands.getIcon()).toString()) > 0) {

					int cms_brandId = getBrandsIdByMongoId(
							brands.getMongo_id(), ip, dbUser, dbPassword);

					List<BasicDBObject> versi = brands.getCategories();
					for (int i = 0; i < versi.size(); i++) {
						Object object_cate = versi.get(i);
						runner.update(conn, link_sql, object_cate.toString(),
								cms_brandId);
						// System.out.println(cms_brandId);
					}
					System.out.println("11");
					tag = "0";
				}
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("同步品牌出错"+e.getMessage());
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 同步分类
	 */
	@Override
	public String initCategoriesInfo(String mallId, String ip, String dbUser,
			String dbPassword) {
		List<Categories> cateList = categoriesServiceImpl
				.getCategoryByMallId(mallId);
		String tag = "1";
		Connection conn = null;
		try {
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "INSERT INTO categories(id,name,old_code,old_id,updated_at,position,parent_id) VALUES(?,?,?,?,?,?,?)";
			QueryRunner runner = new QueryRunner();
			for (Categories cate : cateList) {
				if (cate.getParent_id().equalsIgnoreCase(
						"51f9da1731d65584ab001f0f")) {
					cate.setParent_id("-1");
				}
				if (runner.update(conn, sql, cate.getId(), cate.getName(),
						cate.getOld_code(), cate.getOld_id(),
						cate.getUpdated_at(), cate.getPosition(),
						cate.getParent_id()) > 0) {
					tag = "0";
				}
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("同步分类出错"+e.getMessage());
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 初始化图片
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String initPhotosInfo(String mallId, String ip, String dbUser,
			String dbPassword) {
		List<Photo> photoList = photosServiceImpl.getPhotosByMallId(mallId);
		System.out.println(photoList.size());
		String tag = "1";
		Connection conn = null;
		try {
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "INSERT INTO photos(created_at,file,owner_id,owner_type,position,updated_at,owner_mongo_id,file_url,mongo_id) VALUES(?,?,?,?,?,?,?,?,?)";
			String file_url = "http://rongyi.b0.upaiyun.com/system/photo/file/";
			QueryRunner runner = new QueryRunner();
			for (Photo photo : photoList) {
				int shop_id = getShopsIdByMongoId(photo.getOwner_mongo_id(),
						ip, dbUser, dbPassword);
				if (runner
						.update(conn,
								sql,
								photo.getCreated_at(),
								photo.getFile(),
								shop_id,
								photo.getOwner_type(),
								photo.getPosition(),
								photo.getUpdated_at(),
								photo.getOwner_mongo_id(),
								new StringBuilder(file_url)
										.append(photo.getCreated_at().getYear() + 1900)
										.append("/")
										.append(photo.getCreated_at()
												.getMonth() + 1).append("/")
										.append(photo.getMongo_id())
										.append("/").append(photo.getFile())
										.toString(), photo.getMongo_id()) > 0) {
					tag = "0";
				}
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("初始化图片出错"+e.getMessage());
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 私有 方法获取Connection
	 * 
	 * @param type
	 * @param host
	 * @param port
	 * @param name
	 * @param username
	 * @param password
	 * @return Connection
	 */
	private Connection openConnection(String type, String host, String port,
			String name, String username, String password) {
		Connection conn = null;
		try {
			String driver = null;
			String url = null;

			if (type.equalsIgnoreCase("MySQL")) {
				driver = "com.mysql.jdbc.Driver";
				url = "jdbc:mysql://" + host + ":" + port + "/" + name
						+ "?useUnicode=true&characterEncoding=utf8";
			} else if (type.equalsIgnoreCase("Oracle")) {
				driver = "oracle.jdbc.driver.OracleDriver";
				url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + name;
			} else if (type.equalsIgnoreCase("SQLServer")) {
				driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
				url = "jdbc:sqlserver://" + host + ":" + port
						+ ";databaseName=" + name;
			}
			DbUtils.loadDriver(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			logger.error("获取Connection"+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获取cms数据库中brand主键出错
	 * @param mongo_brand
	 * @param ip
	 * @param dbUser
	 * @param dbPassword
	 * @return int cms中brand的id
	 */
	private int getBrandsIdByMongoId(String mongo_brand, String ip,
			String dbUser, String dbPassword) {
		int reBrandId = -1;
		Connection conn = null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "select id from brands where mongo_id='" + mongo_brand
					+ "'";
			Map<String, Object> map = runner.query(conn, sql, new MapHandler(),
					(Object[]) null);
			
			if(map!=null){
				reBrandId = Integer.parseInt(map.get("id").toString());
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("获取cms数据库中brand主键出错"+e.getMessage());
			e.printStackTrace();
		}
		return reBrandId;
	}

	/**
	 * @param mongo_brand
	 * @param ip
	 * @param dbUser
	 * @param dbPassword
	 * @return int 店铺cms数据库id
	 */
	private int getShopsIdByMongoId(String mongo_shops, String ip,
			String dbUser, String dbPassword) {
		int reShopId = -1;
		Connection conn = null;
		try {
			QueryRunner runner = new QueryRunner();
			conn = this.openConnection("MySQL", ip, "3306", "cms", dbUser,
					dbPassword);
			String sql = "select id from shops where mongo_id='" + mongo_shops
					+ "'";
			Map<String, Object> map = runner.query(conn, sql, new MapHandler(),
					(Object[]) null);
			if(map!=null){
				reShopId = Integer.parseInt(map.get("id").toString());
			}
			DbUtils.close(conn);
		} catch (SQLException e) {
			logger.error("店铺cms数据库id主键出错"+e.getMessage());
			e.printStackTrace();
		}
		return reShopId;
	}

	public ICategoriesService getCategoriesServiceImpl() {
		return categoriesServiceImpl;
	}

	public void setCategoriesServiceImpl(
			ICategoriesService categoriesServiceImpl) {
		this.categoriesServiceImpl = categoriesServiceImpl;
	}

	public IBrandsService getBrandsServiceImpl() {
		return brandsServiceImpl;
	}

	public void setBrandsServiceImpl(IBrandsService brandsServiceImpl) {
		this.brandsServiceImpl = brandsServiceImpl;
	}

	public IShopsService getShopsServiceImpl() {
		return shopsServiceImpl;
	}

	public void setShopsServiceImpl(IShopsService shopsServiceImpl) {
		this.shopsServiceImpl = shopsServiceImpl;
	}

	public IAdzonesService getAdzonesServiceImpl() {
		return adzonesServiceImpl;
	}

	public void setAdzonesServiceImpl(IAdzonesService adzonesServiceImpl) {
		this.adzonesServiceImpl = adzonesServiceImpl;
	}

	public IAdvertisementsService getAdvertisementsServiceImpl() {
		return advertisementsServiceImpl;
	}

	public void setAdvertisementsServiceImpl(
			IAdvertisementsService advertisementsServiceImpl) {
		this.advertisementsServiceImpl = advertisementsServiceImpl;
	}

	public IMallService getMallServiceImpl() {
		return mallServiceImpl;
	}

	public void setMallServiceImpl(IMallService mallServiceImpl) {
		this.mallServiceImpl = mallServiceImpl;
	}

	public IPhotosService getPhotosServiceImpl() {
		return photosServiceImpl;
	}

	public void setPhotosServiceImpl(IPhotosService photosServiceImpl) {
		this.photosServiceImpl = photosServiceImpl;
	}

}
