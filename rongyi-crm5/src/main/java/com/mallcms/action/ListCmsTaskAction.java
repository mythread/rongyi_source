package com.mallcms.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.gcrm.action.crm.BaseListAction;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;
import com.mallcms.domain.Advertisements;
import com.mallcms.domain.AdvertisementsPojo;
import com.mallcms.domain.Brands;
import com.mallcms.domain.BrandsPojo;
import com.mallcms.domain.CmsTask;
import com.mallcms.domain.MallIpConfig;
import com.mallcms.domain.Photo;
import com.mallcms.domain.PhotosPojo;
import com.mallcms.domain.Shops;
import com.mallcms.domain.ShopsPojo;
import com.mallcms.service.IAdvertisementsService;
import com.mallcms.service.IBrandsService;
import com.mallcms.service.IPhotosService;
import com.mallcms.service.IShopsService;

/**
 * Lists cmsTask
 */
public class ListCmsTaskAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;
    private static final String              ADVERT_DIR       = "/upload/advertise/original/";
    private static final String              MINADVERT_DIR    = "/upload/advertise/resize/";
    private static final String              BRAND_DIR        = "/upload/brands/original/";
    private static final String              SHOP_DIR         = "/upload/shops/original/";
	private IBaseService<CmsTask> baseService;
	private IBaseService<ShopsPojo> ShopsbaseService;
	private IBaseService<AdvertisementsPojo> adbaseService;
	private IBaseService<PhotosPojo> photobaseService;
	private IBaseService<BrandsPojo> BrandbaseService;
	private IBaseService<MallIpConfig>mallConfigService;
	private IShopsService shopService;
	private IAdvertisementsService advertisementsService;
	private IPhotosService photoService;
	private IBrandsService brandsService;

	private static final String CLAZZ = CmsTask.class.getSimpleName();
	private int taskid;
	private int type;
	private int reviewStatus;
	private String mallId;
	private ShopsPojo shopsPojo;
	private AdvertisementsPojo adverPojo;
	private BrandsPojo brandsPojo;
	

	

	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	@Override
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<CmsTask> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<CmsTask> cmstasks = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(cmstasks, totalRecords, null, false);
		return null;
	}

	

	/**
	 * @author ShaoYanbin
	 * 任务审核列表
	 * @return null
	 */
	public String listFull() throws Exception {

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		User loginUser = UserUtil.getLoginUser();

		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser.getScope_account(), loginUser);
		SearchResult<CmsTask> result = null;
		if (searchCondition == null) {
			searchCondition = new SearchCondition(1, 15, "id", "desc", "");
			result = new SearchResult<CmsTask>(0, new ArrayList<CmsTask>());
		} else {
			result = baseService.getPaginationObjects(CLAZZ, searchCondition);
		}
		Iterator<CmsTask> cmstasks = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(cmstasks, totalRecords, searchCondition, true);
		return null;
	}
	
	//弹层查看
	public String editCmsTask() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("taskid", taskid);
		request.setAttribute("mallId", mallId);
		request.setAttribute("reviewStatus", reviewStatus);
		String mallConfigHql = "from MallIpConfig where mallId = '"+mallId+"'" ;
        List<MallIpConfig> mallConfigList = mallConfigService.findByHQL(mallConfigHql);
        String domain="";
        if(mallConfigList!=null){
        	domain=mallConfigList.get(0).getDomain();
        }
		Advertisements mongoad=null;
		Shops mongoShop=null;
		String mongoAdZonename=null;
		if(type==0){
			List<ShopsPojo> list = ShopsbaseService.findByHQL("from ShopsPojo where taskId="+taskid);
			if(list.size()==1){
				shopsPojo = list.get(0);
				request.setAttribute("type",0);
				request.setAttribute("shopsPojo", shopsPojo);
				//图片
				List<PhotosPojo> imglist = photobaseService.findByHQL("from PhotosPojo where ownerId="+shopsPojo.getCmsOuterId()+" and ownerType='Shop' and mallId='"+mallId+"'");
				for(PhotosPojo photos:imglist){
					photos.setFile(domain+SHOP_DIR+photos.getFile());
				}
				request.setAttribute("imglist", imglist);
				//品牌
				List<BrandsPojo> brandList = BrandbaseService.findByHQL("from BrandsPojo where cmsOuterId='"+shopsPojo.getBrandId()+"'and mallId='"+mallId+"' and status=0");
				if(brandList!=null&brandList.size()!=0){
					brandsPojo=brandList.get(0);
					brandsPojo.setIcon(domain+BRAND_DIR+brandsPojo.getIcon());
					request.setAttribute("brandsPojo", brandsPojo);
				}
				if(null!=shopsPojo.getMongoId()){
				 mongoShop = shopService.getShopById(shopsPojo.getMongoId());
				 request.setAttribute("mongoShop", mongoShop);
				 //图片
				 List<Photo> mongoImgList = photoService.getPhotosByOwnId(shopsPojo.getMongoId());
				 request.setAttribute("mongoImgList", mongoImgList);
				 
				//获得品牌
				if(mongoShop.getBrand_id()!=null){
				Brands mongoBrand = brandsService.getBrandsBybrandId(mongoShop.getBrand_id());
				request.setAttribute("mongoBrand", mongoBrand);
				}
				}
			}else{
				
			}
		}
		if(type==1){
			List<AdvertisementsPojo> list = adbaseService.findByHQL("from AdvertisementsPojo where taskId="+taskid);
			if(list.size()==1){
				adverPojo = list.get(0);
				request.setAttribute("type",1);
				adverPojo.setPicture(picUrl(adverPojo.getPicture(),1));
				adverPojo.setMinPicture(picUrl(adverPojo.getMinPicture(),3));
				request.setAttribute("adverPojo", adverPojo);
				String adZonename=null;
				if(null!=adverPojo.getAdZoneId()){
					adZonename = advertisementsService.getAdzoneNameById(adverPojo.getAdZoneId());
					request.setAttribute("adZonename", adZonename);
				}
				
				//mongo
				if(adverPojo.getMongodbId()!=null){
				   mongoad = advertisementsService.getAdvertisementsById(adverPojo.getMongodbId());
				   if(null!=mongoad.getAd_zones_id()){
				   mongoAdZonename = advertisementsService.getAdzoneNameById(mongoad.getAd_zones_id());
				   }
				   request.setAttribute("mongoad", mongoad);
				   request.setAttribute("mongoAdZonename", mongoAdZonename);
				}
			}else{
				
			}
		}
		
		return SUCCESS;
	}
	
	
	//审核列表检索
	protected SearchCondition getSearchCondition(
			Map<String, String> fieldTypeMap, int scope, User loginUser)
			throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
        StringBuilder condition = new StringBuilder("(type=1 Or type=0)");
        if (super.getFilters() != null && super.getFilters().trim().length() > 0) {
            advancedSearch(condition);
        } else {
            HashMap parameters = (HashMap) request.getParameterMap();
            Iterator iterator = parameters.keySet().iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
             // TODO:中文乱码
                String[] value = ((String[]) parameters.get(key));
                //String val = value[0];
                String val = new String(value[0].getBytes("iso8859-1"));
                if (value == null || StringUtils.isEmpty(value[0])) {
                    continue;
                }
                if (!BaseListAction.GRID_FIELD_SET.contains(key)) {
                    if (StringUtils.equals(key, "taskType")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("type").append(" in (");
                        for (int i = 0; i < value.length; i++) {
                            condition.append("'").append(value[i]).append("'");
                            if (i < (value.length - 1)) {
                                condition.append(",");
                            }
                        }
                        condition.append(")");
                    } else if (StringUtils.equals(key, "taskreviewStatus")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("reviewStatus").append(" in (");
                        for (int i = 0; i < value.length; i++) {
                            condition.append("'").append(value[i]).append("'");
                            if (i < (value.length - 1)) {
                                condition.append(",");
                            }
                        }
                        condition.append(")");
                    } 
                    else if (StringUtils.equals(key, "startAssignDate")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("gmtJobCreate").append(" >= '").append(val).append("' ");
                    } else if (StringUtils.equals(key, "endAssignDate")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("gmtModified").append(" <= '").append(val).append("' ");
                    } else {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }

                        if (NumberUtils.toFloat(val) != 0.0f) {
                            condition.append("mallId").append(" = ").append(val);
                        } else {
                                condition.append("mallName").append(" like '%").append(val).append("%'").append(" OR ")
                                .append("reviewInfo").append(" like '%").append(val).append("%'");
                        }
                    }
                }
            }
        }
		

		int pageNo = super.getPage();
		if (pageNo == 0) {
			pageNo = 1;
		}

		int pageSize = super.getRows();
		if (pageSize == 0) {
			pageSize = 1;
		}
		super.setSidx("gmtModified");
		super.setSord("desc");
		SearchCondition searchCondition = new SearchCondition(pageNo, pageSize,
				super.getSidx(), super.getSord(), condition.toString());
		return searchCondition;

	}

	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public static void getListJson(Iterator<CmsTask> cmstasks,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (cmstasks.hasNext()) {
			CmsTask instance = cmstasks.next();
			int id = instance.getId();
			String mall_id = instance.getMallId();
			String reviewInfo = instance.getReviewInfo();
			String mallName = CommonUtil.stringToJson(CommonUtil
					.fromNullToEmpty(instance.getMallName()));
			String taskType = "";
			int type = instance.getType();
			if (type == 1) {
				taskType = "广告信息";
			}
			if (type == 0) {
				taskType = "商铺信息";
			}

			Integer reviewStatus = instance.getReviewStatus();
			String taskreviewStatus = "";
			if (reviewStatus == 1) {
				taskreviewStatus = "待审核";
			}
			if (reviewStatus == 2) {
				taskreviewStatus = "已审核";
			}
			if (reviewStatus == 3) {
				taskreviewStatus = "驳回";
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Constant.DATE_FULL_FORMAT);
			Date gmtCreate = instance.getGmtModified();
			String gmtJobCreateName = "";
			if (gmtCreate != null) {
				gmtJobCreateName = dateFormat.format(gmtCreate);
			}
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
						.append(mallName).append("\",\"")
						.append(reviewInfo).append("\",\"");
				jsonBuilder.append(taskType).append("\",\"")
						.append(taskreviewStatus).append("\",\"")
						.append(gmtJobCreateName).append("\",\"")
						.append("<button mallId='"+mall_id+"' onclick='OpenFrame("+id+","+type+","+reviewStatus+",this)'>查看</button>")
						.append("\"]}");
			}
			if (cmstasks.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
	//获取图片路径
	public String picUrl(String pic,int type){
		String picurl="";
		String mallConfigHql = "from MallIpConfig where mallId = '"+mallId+"'" ;
        List<MallIpConfig> mallConfigList = mallConfigService.findByHQL(mallConfigHql);
        String domain = mallConfigList.get(0).getDomain();
        if(type==0){
        	picurl=domain+SHOP_DIR+pic;
        	return picurl;
        }
        if(type==1){
        	picurl=domain+ADVERT_DIR+pic;
        	return picurl;
        }
        if(type==2){
        	picurl=domain+BRAND_DIR+pic;
        	return picurl;
        }
        if(type==3){
        	picurl=domain+MINADVERT_DIR+pic;
        	return picurl;
        }
       return null;
	}
	//获取图片路径
	public List listPicUrl(List list,int type){
		List picurl_list=new ArrayList();
		String mallConfigHql = "from MallIpConfig where mallId = '"+mallId+"'" ;
		List<MallIpConfig> mallConfigList = mallConfigService.findByHQL(mallConfigHql);
		String domain = mallConfigList.get(0).getDomain();
		if(type==0){
			for(int i=0;i<list.size();i++){
				
			}
			return picurl_list;
		}
		if(type==1){
			return picurl_list;
		}
		if(type==2){
			return picurl_list;
		}
		return null;
	}
	public IBaseService<ShopsPojo> getShopsbaseService() {
		return ShopsbaseService;
	}

	public void setShopsbaseService(IBaseService<ShopsPojo> shopsbaseService) {
		ShopsbaseService = shopsbaseService;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	@Override
	protected String getEntityName() {
		return CmsTask.class.getSimpleName();
	}

	/**
	 * @return the id
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public IBaseService<CmsTask> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<CmsTask> baseService) {
		this.baseService = baseService;
	}

	public IShopsService getShopService() {
		return shopService;
	}

	public void setShopService(IShopsService shopService) {
		this.shopService = shopService;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public ShopsPojo getShopsPojo() {
		return shopsPojo;
	}

	public void setShopsPojo(ShopsPojo shopsPojo) {
		this.shopsPojo = shopsPojo;
	}

	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	
	public AdvertisementsPojo getAdverPojo() {
		return adverPojo;
	}

	public void setAdverPojo(AdvertisementsPojo adverPojo) {
		this.adverPojo = adverPojo;
	}

	public IAdvertisementsService getAdvertisementsService() {
		return advertisementsService;
	}

	public void setAdvertisementsService(IAdvertisementsService advertisementsService) {
		this.advertisementsService = advertisementsService;
	}

	public IBaseService<AdvertisementsPojo> getAdbaseService() {
		return adbaseService;
	}

	public void setAdbaseService(IBaseService<AdvertisementsPojo> adbaseService) {
		this.adbaseService = adbaseService;
	}



	public String getMallId() {
		return mallId;
	}



	public void setMallId(String mallId) {
		this.mallId = mallId;
	}



	public IBaseService<PhotosPojo> getPhotobaseService() {
		return photobaseService;
	}



	public void setPhotobaseService(IBaseService<PhotosPojo> photobaseService) {
		this.photobaseService = photobaseService;
	}



	public IPhotosService getPhotoService() {
		return photoService;
	}



	public void setPhotoService(IPhotosService photoService) {
		this.photoService = photoService;
	}



	public IBaseService<BrandsPojo> getBrandbaseService() {
		return BrandbaseService;
	}



	public void setBrandbaseService(IBaseService<BrandsPojo> brandbaseService) {
		BrandbaseService = brandbaseService;
	}



	public BrandsPojo getBrandsPojo() {
		return brandsPojo;
	}



	public void setBrandsPojo(BrandsPojo brandsPojo) {
		this.brandsPojo = brandsPojo;
	}



	public IBrandsService getBrandsService() {
		return brandsService;
	}



	public void setBrandsService(IBrandsService brandsService) {
		this.brandsService = brandsService;
	}



	public int getReviewStatus() {
		return reviewStatus;
	}



	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}



	public IBaseService<MallIpConfig> getMallConfigService() {
		return mallConfigService;
	}



	public void setMallConfigService(IBaseService<MallIpConfig> mallConfigService) {
		this.mallConfigService = mallConfigService;
	}
}
