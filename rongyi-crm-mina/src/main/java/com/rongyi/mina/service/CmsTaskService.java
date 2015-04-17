package com.rongyi.mina.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongyi.mina.bean.AdvertTaskImpl;
import com.rongyi.mina.bean.Advertisements;
import com.rongyi.mina.bean.BrandTaskImpl;
import com.rongyi.mina.bean.Brands;
import com.rongyi.mina.bean.CmsTask;
import com.rongyi.mina.bean.Photos;
import com.rongyi.mina.bean.ShopTaskImpl;
import com.rongyi.mina.bean.Shops;
import com.rongyi.mina.bean.TaskInterface;
import com.rongyi.mina.constant.JobConstant;
import com.rongyi.mina.dao.AdvertisementsDao;
import com.rongyi.mina.dao.BrandsDao;
import com.rongyi.mina.dao.CmsTaskDao;
import com.rongyi.mina.dao.PhotosDao;
import com.rongyi.mina.dao.ShopsDao;

/**
 * @author jiejie 2014年4月3日 下午6:02:50
 */
public class CmsTaskService {

    private static final Logger LOG       = LoggerFactory.getLogger(CmsTaskService.class);

    private static final String JOB_TYPE  = "type";
    private static final String MALL_NAME = "param_mallName";

    @Autowired
    private CmsTaskDao          cmsTaskDao;

    @Autowired
    private AdvertisementsDao   advertDao;

    @Autowired
    private BrandsDao           brandsDao;

    @Autowired
    private ShopsDao            shopsDao;

    @Autowired
    private PhotosDao           photosDao;

    /**
     * 插入task任务和相对应的商铺、品牌、广告
     */
    @SuppressWarnings("rawtypes")
    public Integer insertTasks(HashMap<String, Object> msgMap) throws Exception {
        if (msgMap == null || msgMap.isEmpty()) {
            return null;
        }
        TaskInterface itask = null;
        Integer type = (Integer) msgMap.get(JOB_TYPE);
        String mallName = (String) msgMap.get(MALL_NAME);
        if (type == JobConstant.JobType.SHOP_TYPE) {
            itask = new ShopTaskImpl(msgMap, Shops.class);
            return insertShop((ShopTaskImpl) itask, mallName);
        } else if (type == JobConstant.JobType.ADVERT_TYPE) {
            itask = new AdvertTaskImpl(msgMap, Advertisements.class);
            return insertAdvert((AdvertTaskImpl) itask, mallName);
        } else if (type == JobConstant.JobType.BRAND_TYPE) {
            itask = new BrandTaskImpl(msgMap, Brands.class);
            return insertBrand((BrandTaskImpl) itask);
        }
        return null;
    }

    /**
     * 插入广告
     */
    private Integer insertAdvert(AdvertTaskImpl advertImpl, String mallName) throws Exception {
        Advertisements ad = advertImpl.getTask();
        CmsTask cmsTask = advertImpl.getCmsTask();
        if (cmsTask == null) {
            return null;
        }
        int adId = 0;
        LOG.info("insertAdvert>>>mallId:" + cmsTask.getMallId() + ";cmsOuterId:" + ad.getCmsOuterId());
        Advertisements tmp = advertDao.selectByParam(cmsTask.getMallId(), ad.getCmsOuterId());
        if (tmp != null && tmp.getTaskId() != null && tmp.getId() != null) {
            LOG.info("已经存在待处理的当前广告,则更新广告,更新任务修改时间");
            LOG.info("taskId:" + tmp.getTaskId() + ";adId:" + tmp.getId());
            cmsTaskDao.updateGmtModifiedById(tmp.getTaskId(), mallName);
            ad.setTaskId(tmp.getTaskId());
            ad.setId(tmp.getId());
            advertDao.update(ad);
            adId = tmp.getId();
        } else {
            LOG.info("未查询到待处理的当前广告,则新增任务和广告");
            Integer taskId = cmsTaskDao.insert(cmsTask);
            if (taskId == null) {
                return null;
            }
            ad.setTaskId(taskId);
            adId = advertDao.insert(ad);
        }
        return adId;
    }

    /**
     * 插入品牌
     */
    private Integer insertBrand(BrandTaskImpl brandImpl) {
        Brands brand = brandImpl.getTask();
        CmsTask cmsTask = brandImpl.getCmsTask();
        if (cmsTask == null) {
            return null;
        }

        int brandsId = 0;
        LOG.info("insertBrand>>>mallId:" + cmsTask.getMallId() + ";cmsOuterId:" + brand.getCmsOuterId());
        Brands brands = brandsDao.selectByParam(cmsTask.getMallId(), brand.getCmsOuterId());
        if (brands == null) {
            Integer taskId = cmsTaskDao.insert(cmsTask);
            if (taskId == null) {
                return null;
            }
            brand.setTaskId(taskId);
            brandsId = brandsDao.insert(brand);
            return brandsId;
        } else {

            brandsDao.update(brands);
            return brands.getId();
        }

    }

    /**
     * 插入商铺
     */
    private Integer insertShop(ShopTaskImpl shopImpl, String mallName) throws Exception {
        Shops shop = shopImpl.getTask();
        CmsTask cmsTask = shopImpl.getCmsTask();
        if (cmsTask == null) {
            return null;
        }

        int shopsId = 0;
        LOG.info("insertBrand>>>mallId:" + cmsTask.getMallId() + ";cmsOuterId:" + shop.getCmsOuterId());
        Shops tmp = shopsDao.selectByParam(cmsTask.getMallId(), shop.getCmsOuterId());
        if (tmp != null && tmp.getTaskId() != null && tmp.getId() != null) {
            LOG.info("已经存在待处理的当前商家,则更新商家,更新任务修改时间");
            LOG.info("taskId:" + tmp.getTaskId() + ";adId:" + tmp.getId());
            cmsTaskDao.updateGmtModifiedById(tmp.getTaskId(), mallName);
            insertPhotos(shopImpl);
            shop.setTaskId(tmp.getTaskId());
            shop.setId(tmp.getId());
            shopsDao.update(shop);
            shopsId = tmp.getId();
        } else {
            LOG.info("未查询到待处理的当前商家,则新增任务和商家");
            Integer taskId = cmsTaskDao.insert(cmsTask);
            if (taskId == null) {
                return null;
            }
            insertPhotos(shopImpl);
            shop.setTaskId(taskId);
            shopsId = shopsDao.insert(shop);
        }
        return shopsId;
    }

    /**
     * 插入图片
     * 
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void insertPhotos(TaskInterface taskImpl) {
        List<Photos> photoList = taskImpl.getPhotosList();
        if (photoList == null || photoList.isEmpty()) {
            return;
        }
        Photos p_ = photoList.get(0);
        photosDao.delete(p_.getOwnerId(), p_.getOwnerType(), p_.getMallId());
        for (Photos p : photoList) {
            photosDao.insert(p);
        }
    }

    public List<Photos> listPhotosByOwnerId(Integer ownerId, String ownerType, String mallId) {
        return photosDao.listByOwnerId(ownerId, ownerType, mallId);
    }
}
