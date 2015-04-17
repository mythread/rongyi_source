package com.mallcms.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.common.HttpClientUtils;
import com.gcrm.service.IBaseService;
import com.mallcms.Constant.CmsConstant;
import com.mallcms.domain.AdvertisementsPojo;
import com.mallcms.domain.BrandsPojo;
import com.mallcms.domain.CmsTask;
import com.mallcms.domain.CrmJob;
import com.mallcms.domain.MallIpConfig;
import com.mallcms.domain.PhotosPojo;
import com.mallcms.domain.ShopsPojo;
import com.mallcms.service.IAdvertisementsService;
import com.mallcms.service.IBrandsService;
import com.mallcms.service.IPhotosService;
import com.mallcms.service.IShopsService;
import com.upyun.UpYun;

/**
 * 处理同步的action
 * 
 * @author jiejie 2014年4月10日 下午2:17:55
 */
public class CmsSynchAction extends CmsBaseAction {

    private static final long                serialVersionUID = -5796807771605947532L;
    private static final String              PIC_DOMAIN       = "http://rongyi.b0.upaiyun.com";
    private static final String              BUCKET_NAME      = "rongyi";                      // 云服务器上的空间名
    private static final String              ADVERT_DIR       = "/upload/advertise/original/";
    private static final String              BRAND_DIR        = "/upload/brands/original/";
    private static final String              SHOP_DIR         = "/upload/shops/original/";
    private static final String              YUN_USERNAME     = "gongzhen";
    private static final String              YUN_PASSWORD     = "rynw87^%43";

    private IBaseService<AdvertisementsPojo> advertService;
    private IBaseService<ShopsPojo>          shopsService;
    private IBaseService<CmsTask>            cmsTaskService;
    private IBaseService<CrmJob>             crmJobService;
    private IBaseService<PhotosPojo>         photosService;
    private IBaseService<BrandsPojo>         brandsService;
    private IBaseService<MallIpConfig>       mallConfigService;
    private IAdvertisementsService           mongoAdvertService;
    private IShopsService                    mongoShopsService;
    private IPhotosService                   mongoPhotoService;
    private IBrandsService                   mongoBrandService;
    private Integer                          taskId;
    private Integer                          reviewStatus;
    private String                           mallId;
    private String                           showInfo;
    private String                           brandId;

    /**
     * 广告审核通过
     */
    public String advertSynchPass() throws Exception {
        if (taskId == null) {
            response2Json(null, false, "审核任务ID为空");
            return null;
        }
        CmsTask cmsTask = cmsTaskService.getEntityById(CmsTask.class, taskId);
        if (cmsTask == null) {
            response2Json(null, false, "审核数据不存在");
            return null;
        }
        cmsTask.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_END);
        cmsTask.setGmtModified(new Date());
        cmsTask.setTaskStatus(CmsConstant.TaskStatus.END_HANDLE);
        cmsTask = cmsTaskService.makePersistent(cmsTask);
        String hql = "from AdvertisementsPojo where taskId = " + cmsTask.getId();
        List<AdvertisementsPojo> adList = advertService.findByHQL(hql);
        if (adList == null || adList.isEmpty()) {
            response2Json(null, false, "审核数据不存在");
            return null;
        }
        AdvertisementsPojo pojo = adList.get(0);
        // 插入mongodb
        String mongodbId = mongoAdvertService.insert2Mongodb(pojo);
        pojo.setMongodbId(mongodbId);
        // 更新mongodbId字段
        String picUrl = getPicUrl(mongodbId, pojo.getPicture(), CmsConstant.TaskType.ADVERT_TYPE);
        pojo.setPictureUrl(picUrl);
        advertService.makePersistent(pojo);

        String mallConfigHql = "from MallIpConfig where mallId = '" + cmsTask.getMallId() + "'";
        List<MallIpConfig> mallConfigList = mallConfigService.findByHQL(mallConfigHql);
        if (mallConfigList == null || mallConfigList.isEmpty()) {
            response2Json(null, false, "商场配置数据不存在");
            return null;
        }
        uploadPicByThread(mongodbId, mallConfigList.get(0).getDomain(), pojo.getPicture(),
                          CmsConstant.TaskType.ADVERT_TYPE);

        // 插入job
        CrmJob job = insertCrmJob(cmsTask, pojo.getCmsOuterId(), mongodbId, picUrl, true);
        response2Json(cmsTask.getId(), true, "【审核通过】操作成功!");
        return null;
    }

    /**
     * 广告被驳回
     */
    public String advertSynchFail() throws Exception {
        if (taskId == null) {
            response2Json(null, false, "审核任务ID为空");
            return null;
        }
        CmsTask cmsTask = cmsTaskService.getEntityById(CmsTask.class, taskId);
        if (cmsTask == null) {
            response2Json(null, false, "审核数据不存在");
            return null;
        }
        cmsTask.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_FAIL);
        cmsTask.setGmtModified(new Date());
        cmsTask.setTaskStatus(CmsConstant.TaskStatus.END_HANDLE);
        cmsTask = cmsTaskService.makePersistent(cmsTask);
        String hql = "from AdvertisementsPojo where taskId = " + cmsTask.getId();
        List<AdvertisementsPojo> adList = advertService.findByHQL(hql);
        AdvertisementsPojo pojo = adList.get(0);
        // 插入job
        CrmJob job = insertCrmJob(cmsTask, pojo.getCmsOuterId(), null, null, false);
        response2Json(cmsTask.getId(), true, "【审核不通过】操作成功!");
        return null;
    }

    /**
     * 商铺审核通过
     */
    public String shopsSynchPass() throws Exception {
        if (taskId == null) {
            response2Json(null, false, "审核任务ID为空");
            return null;
        }
        CmsTask cmsTask = cmsTaskService.getEntityById(CmsTask.class, taskId);
        if (cmsTask == null) {
            response2Json(null, false, "审核数据不存在");
            return null;
        }
        cmsTask.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_END);
        cmsTask.setGmtModified(new Date());
        cmsTask.setTaskStatus(CmsConstant.TaskStatus.END_HANDLE);
        cmsTask = cmsTaskService.makePersistent(cmsTask);
        String hql = "from ShopsPojo where taskId = " + cmsTask.getId();
        List<ShopsPojo> shopList = shopsService.findByHQL(hql);
        if (shopList == null || shopList.isEmpty()) {
            response2Json(null, false, "审核数据不存在");
            return null;
        }

        // 品牌审核通过
        BrandsPojo brand = brandSynchPass();
        ShopsPojo pojo = shopList.get(0);
        if (brand != null) {
            pojo.setMongoBrandId(brand.getMongoId());
            shopsService.makePersistent(pojo);
        }
        // 更新mongodb中的数据
        mongoShopsService.updateShop(pojo);

        String mallConfigHql = "from MallIpConfig where mallId ='" + cmsTask.getMallId() + "'";
        List<MallIpConfig> mallConfigList = mallConfigService.findByHQL(mallConfigHql);
        if (mallConfigList == null || mallConfigList.isEmpty()) {
            response2Json(null, false, "商场配置数据不存在");
            return null;
        }

        // 图片处理
        String photoHql = "from PhotosPojo where ownerId = " + pojo.getCmsOuterId() + "and ownerType = \'"
                          + CmsConstant.PhotosOwnerType.SHOP_TYPE + "\'";
        List<PhotosPojo> photoList = photosService.findByHQL(photoHql);
        if (photoList != null && photoList.size() > 0) {
            for (PhotosPojo photo : photoList) {
                String photoMongoId = mongoPhotoService.update2Mongodb(photo);
                if (photoMongoId != null) {
                    photo.setMongoId(photoMongoId);
                    photo.setFileUrl(getPicUrl(photo));
                    photosService.makePersistent(photo);
                }
            }
            uploadPicByThread(mallConfigList.get(0).getDomain(), photoList);
        }
        // 插入job
        insertCrmJob(cmsTask, pojo.getCmsOuterId(), pojo.getMongoId(), null, true);

        response2Json(cmsTask.getId(), true, "【审核通过】操作成功!");
        return null;
    }

    /**
     * 商铺审核失败
     */
    public String shopsSynchFail() throws Exception {
        if (taskId == null) {
            response2Json(null, false, "审核任务ID为空");
            return null;
        }
        CmsTask cmsTask = cmsTaskService.getEntityById(CmsTask.class, taskId);
        if (cmsTask == null) {
            response2Json(null, false, "审核数据不存在");
            return null;
        }
        cmsTask.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_FAIL);
        cmsTask.setGmtModified(new Date());
        cmsTask.setTaskStatus(CmsConstant.TaskStatus.END_HANDLE);
        cmsTask = cmsTaskService.makePersistent(cmsTask);
        String hql = "from ShopsPojo where taskId = " + cmsTask.getId();
        List<ShopsPojo> shopList = shopsService.findByHQL(hql);
        ShopsPojo pojo = shopList.get(0);
        // 插入job
        CrmJob job = insertCrmJob(cmsTask, pojo.getCmsOuterId(), pojo.getMongoId(), null, false);

        // 品牌审核失败
        brandSynchFail();
        response2Json(cmsTask.getId(), true, "【审核不通过】操作成功!");
        return null;

    }

    /**
     * 品牌审核通过
     */
    private BrandsPojo brandSynchPass() throws Exception {
        String hql = "from BrandsPojo where mallId = '" + mallId + "' and cmsOuterId = " + brandId;
        List<BrandsPojo> brandList = brandsService.findByHQL(hql);
        if (brandList == null || brandList.isEmpty()) {
            return null;
        }
        BrandsPojo pojo = brandList.get(0);
        String mongodbId = mongoBrandService.insert(pojo);
        pojo.setMongoId(mongodbId);
        String iconUrl = getPicUrl(mongodbId, pojo.getIcon(), CmsConstant.TaskType.BRAND_TYPE);
        pojo.setIconUrl(iconUrl);
        // status 置为1 表示已经操作过了，不是新品牌
        pojo.setStatus(1);
        brandsService.makePersistent(pojo);

        CmsTask cmsTask = cmsTaskService.getEntityById(CmsTask.class, pojo.getTaskId());
        if (cmsTask == null) {
            return null;
        }
        cmsTask.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_END);
        cmsTask.setGmtModified(new Date());
        cmsTask.setTaskStatus(CmsConstant.TaskStatus.END_HANDLE);
        cmsTask = cmsTaskService.makePersistent(cmsTask);

        String mallConfigHql = "from MallIpConfig where mallId = '" + cmsTask.getMallId() + "'";
        List<MallIpConfig> mallConfigList = mallConfigService.findByHQL(mallConfigHql);
        if (mallConfigList == null || mallConfigList.isEmpty()) {
            return null;
        }
        uploadPicByThread(mongodbId, mallConfigList.get(0).getDomain(), pojo.getIcon(), CmsConstant.TaskType.BRAND_TYPE);

        // 插入job
        CrmJob job = insertCrmJob(cmsTask, pojo.getCmsOuterId(), mongodbId, iconUrl, true);
        if (job.getId() != null) {
            return pojo;
        } else {
            return null;
        }
    }

    /**
     * 品牌审核不通过
     */
    private boolean brandSynchFail() throws Exception {
        String hql = "from BrandsPojo where mallId = '" + mallId + "' and cmsOuterId = " + brandId;
        List<BrandsPojo> brandList = brandsService.findByHQL(hql);
        if (brandList == null || brandList.isEmpty()) {
            return false;
        }
        BrandsPojo pojo = brandList.get(0);
        CmsTask cmsTask = cmsTaskService.getEntityById(CmsTask.class, pojo.getTaskId());
        if (cmsTask == null) {
            return false;
        }
        cmsTask.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_END);
        cmsTask.setGmtModified(new Date());
        cmsTask.setTaskStatus(CmsConstant.TaskStatus.END_HANDLE);
        cmsTask = cmsTaskService.makePersistent(cmsTask);
        // 插入job
        CrmJob job = insertCrmJob(cmsTask, pojo.getCmsOuterId(), null, null, false);
        if (job.getId() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 插入crmjob记录
     * 
     * @param cmsTask
     * @param cmsOuterId
     * @return
     */
    private CrmJob insertCrmJob(CmsTask cmsTask, String cmsOuterId, String mongodbId, String picUrl,
                                boolean synchSuccess) {
        CrmJob job = new CrmJob();
        Date now = new Date();
        job.setGmtCreate(now);
        job.setGmtModified(now);
        job.setJobStatus(0);
        job.setMallId(cmsTask.getMallId());
        if (synchSuccess) {
            job.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_END);
        } else {
            job.setReviewStatus(CmsConstant.SynchState.SYNCHRONOUS_FAIL);
        }
        job.setType(cmsTask.getType());
        job.setCmsOuterId(cmsOuterId);
        job.setPicUrl(picUrl);
        if (!StringUtils.isEmpty(mongodbId)) {
            job.setMongodbId(mongodbId);
        }
        job.setMemo(getShowInfo());
        job = crmJobService.makePersistent(job);
        return job;
    }

    /**
     * 向云服务器插入图片
     * 
     * @param crm数据库中的图片列表
     */
    private void uploadPicByThread(final String domain, final List<PhotosPojo> photosList) {
        // 云服务器 用户名 密码
        final UpYun upYun = new UpYun(BUCKET_NAME, YUN_USERNAME, YUN_PASSWORD);
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (PhotosPojo photo : photosList) {
                    String filePath = getPicPath(photo);
                    if (filePath == null) {
                        continue;
                    }
                    String url = domain + SHOP_DIR + photo.getFile();
                    GetMethod getMethod = new GetMethod(url);
                    byte[] datas = HttpClientUtils.getResponseBodyAsByte(getMethod);
                    if (datas.length > 0) {
                        upYun.writeFile(filePath, datas);
                    }
                }
            }
        }).start();
    }

    private void uploadPicByThread(String mongoId, String domain, String pic, int type) {
        String picUrl = "";
        final String filePath = getPicPath(mongoId, pic, type);
        if (filePath == null) {
            return;
        }
        if (type == CmsConstant.TaskType.ADVERT_TYPE) {
            picUrl = domain + ADVERT_DIR + pic;
        } else if (type == CmsConstant.TaskType.BRAND_TYPE) {
            picUrl = domain + BRAND_DIR + pic;
        } else {
            return;
        }
        final String url = picUrl;
        final UpYun upYun = new UpYun(BUCKET_NAME, YUN_USERNAME, YUN_PASSWORD);
        new Thread(new Runnable() {

            @Override
            public void run() {
                GetMethod getMethod = new GetMethod(url);
                byte[] datas = HttpClientUtils.getResponseBodyAsByte(getMethod);
                if (datas.length > 0) {
                    upYun.writeFile(filePath, datas, true);
                }

            }
        }).start();
    }

    /**
     * 获得图片在服务器上的路径,相对于空间名
     * 
     * @return
     */
    private String getPicPath(PhotosPojo photo) {
        String type = photo.getOwnerType();
        if (StringUtils.equals(type, CmsConstant.PhotosOwnerType.SHOP_TYPE)) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            return "system/photo/file/" + year + "/" + month + "/" + photo.getMongoId() + "/" + photo.getFile();
        } else if (StringUtils.equals(type, CmsConstant.PhotosOwnerType.BRAND_TYPE)) {
            return "system/brand/icon/" + photo.getMongoId() + "/" + photo.getFile();
        } else if (StringUtils.equals(type, CmsConstant.PhotosOwnerType.ADVERT_TYPE)) {
            return "system/advertisement/picture/" + photo.getMongoId() + "/" + photo.getFile();
        }
        return null;
    }

    private String getPicPath(String mongoId, String pic, int type) {
        if (type == CmsConstant.TaskType.ADVERT_TYPE) {
            return "system/advertisement/picture/" + mongoId + "/" + pic;
        } else if (type == CmsConstant.TaskType.BRAND_TYPE) {
            return "system/brand/icon/" + mongoId + "/" + pic;
        }
        return null;
    }

    private String getPicUrl(PhotosPojo photo) {
        return PIC_DOMAIN + "/" + getPicPath(photo);
    }

    private String getPicUrl(String mongoId, String pic, int type) {
        return PIC_DOMAIN + "/" + getPicPath(mongoId, pic, type);
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public IBaseService<AdvertisementsPojo> getAdvertService() {
        return advertService;
    }

    public void setAdvertService(IBaseService<AdvertisementsPojo> advertService) {
        this.advertService = advertService;
    }

    public IBaseService<CmsTask> getCmsTaskService() {
        return cmsTaskService;
    }

    public void setCmsTaskService(IBaseService<CmsTask> cmsTaskService) {
        this.cmsTaskService = cmsTaskService;
    }

    public IBaseService<CrmJob> getCrmJobService() {
        return crmJobService;
    }

    public void setCrmJobService(IBaseService<CrmJob> crmJobService) {
        this.crmJobService = crmJobService;
    }

    public IAdvertisementsService getMongoAdvertService() {
        return mongoAdvertService;
    }

    public void setMongoAdvertService(IAdvertisementsService mongoAdvertService) {
        this.mongoAdvertService = mongoAdvertService;
    }

    public IBaseService<ShopsPojo> getShopsService() {
        return shopsService;
    }

    public void setShopsService(IBaseService<ShopsPojo> shopsService) {
        this.shopsService = shopsService;
    }

    public IShopsService getMongoShopsService() {
        return mongoShopsService;
    }

    public void setMongoShopsService(IShopsService mongoShopsService) {
        this.mongoShopsService = mongoShopsService;
    }

    public IBaseService<PhotosPojo> getPhotosService() {
        return photosService;
    }

    public void setPhotosService(IBaseService<PhotosPojo> photosService) {
        this.photosService = photosService;
    }

    public IPhotosService getMongoPhotoService() {
        return mongoPhotoService;
    }

    public void setMongoPhotoService(IPhotosService mongoPhotoService) {
        this.mongoPhotoService = mongoPhotoService;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public IBaseService<BrandsPojo> getBrandsService() {
        return brandsService;
    }

    public void setBrandsService(IBaseService<BrandsPojo> brandsService) {
        this.brandsService = brandsService;
    }

    public IBrandsService getMongoBrandService() {
        return mongoBrandService;
    }

    public void setMongoBrandService(IBrandsService mongoBrandService) {
        this.mongoBrandService = mongoBrandService;
    }

    public IBaseService<MallIpConfig> getMallConfigService() {
        return mallConfigService;
    }

    public void setMallConfigService(IBaseService<MallIpConfig> mallConfigService) {
        this.mallConfigService = mallConfigService;
    }
}
