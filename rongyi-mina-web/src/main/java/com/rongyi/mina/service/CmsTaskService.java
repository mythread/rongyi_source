package com.rongyi.mina.service;

import java.util.HashMap;
import java.util.List;

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

    private static final String JOB_TYPE = "type";

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
    public Integer insertTasks(HashMap<String, Object> msgMap) {
        if (msgMap == null || msgMap.isEmpty()) {
            return null;
        }
        TaskInterface itask = null;
        Integer type = (Integer) msgMap.get(JOB_TYPE);
        if (type == JobConstant.JobType.SHOP_TYPE) {
            itask = new ShopTaskImpl(msgMap, Shops.class);
            return insertShop((ShopTaskImpl) itask);
        } else if (type == JobConstant.JobType.ADVERT_TYPE) {
            itask = new AdvertTaskImpl(msgMap, Advertisements.class);
            return insertAdvert((AdvertTaskImpl) itask);
        } else if (type == JobConstant.JobType.BRAND_TYPE) {
            itask = new BrandTaskImpl(msgMap, Brands.class);
            return insertBrand((BrandTaskImpl) itask);
        }
        return null;
    }

    /**
     * 插入广告
     */
    private Integer insertAdvert(AdvertTaskImpl advertImpl) {
        Advertisements ad = advertImpl.getTask();
        CmsTask cmsTask = advertImpl.getCmsTask();
        if (cmsTask == null) {
            return null;
        }
        Integer taskId = cmsTaskDao.insert(cmsTask);
        if (taskId == null) {
            return null;
        }
        // insertPhotos(advertImpl);
        ad.setTaskId(taskId);
        return advertDao.insert(ad);
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
        Integer taskId = cmsTaskDao.insert(cmsTask);
        if (taskId == null) {
            return null;
        }
        // insertPhotos(brandImpl);
        brand.setTaskId(taskId);
        return brandsDao.insert(brand);
    }

    /**
     * 插入商铺
     */
    private Integer insertShop(ShopTaskImpl shopImpl) {
        Shops shop = shopImpl.getTask();
        CmsTask cmsTask = shopImpl.getCmsTask();
        if (cmsTask == null) {
            return null;
        }
        Integer taskId = cmsTaskDao.insert(cmsTask);
        if (taskId == null) {
            return null;
        }
        insertPhotos(shopImpl);
        shop.setTaskId(taskId);
        return shopsDao.insert(shop);
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
        for (Photos p : photoList) {
            photosDao.delete(p.getOwnerId(), p.getOwnerType());
            photosDao.insert(p);
        }
    }

}
