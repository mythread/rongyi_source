package com.rongyi.mina.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.rongyi.mina.constant.Constant;
import com.rongyi.mina.constant.JobConstant;
import com.rongyi.mina.mongodao.IAdZonesMongoDao;
import com.rongyi.mina.mongodao.IAdvertMongoDao;
import com.rongyi.mina.mongodao.IShopMongoDao;

/**
 * 类OperateMongoService.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月15日 下午8:12:52
 */
public class OperateMongoService {

    private static final String OPERATE_TYPE      = "operateType";
    private static final String ADVERT_MONGODB_ID = "mongodbId";
    private static final String JOB_TYPE          = "type";
    private static final String AD_ZONE_ID        = "adZoneId";

    @Autowired
    private IAdvertMongoDao     advertMongoDaoImpl;

    @Autowired
    private IAdZonesMongoDao    adZonesMongoDaoImpl;

    @Autowired
    private IShopMongoDao       shopMongoDaoImpl;

    public void operate(HashMap<String, Object> msgMap) {
        int type = (Integer) msgMap.get(JOB_TYPE);
        if (type == JobConstant.JobType.ADVERT_TYPE) {
            operateAdvert(msgMap);
        } else if (type == JobConstant.JobType.SHOP_TYPE) {
            operateShop(msgMap);
        }

    }

    /**
     * 操作mongodb中的广告
     * 
     * @param msgMap
     */
    private void operateAdvert(HashMap<String, Object> msgMap) {
        Integer operateType = (Integer) msgMap.get(OPERATE_TYPE);
        String mongodbId = (String) msgMap.get(ADVERT_MONGODB_ID);
        if (operateType == JobConstant.OperateType.AD_DELETE) {
            advertMongoDaoImpl.delete(mongodbId);
        } else if (operateType == JobConstant.OperateType.AD_TURN_ON) {
            advertMongoDaoImpl.turnOn(mongodbId);
        } else if (operateType == JobConstant.OperateType.AD_TURN_OFF) {
            advertMongoDaoImpl.turnOff(mongodbId);
        } else if (operateType == JobConstant.OperateType.AD_DEFAULT_ON) {
            // 设置默认广告
            advertMongoDaoImpl.setDefaultAd(JobConstant.DefaultAdvert.YES, mongodbId);
            // 插入广告位默认广告
            String adZoneId = (String) msgMap.get(AD_ZONE_ID);
            adZonesMongoDaoImpl.setDefaultPic(mongodbId, adZoneId);
        } else if (operateType == JobConstant.OperateType.AD_DEFAULT_OFF) {
            advertMongoDaoImpl.setDefaultAd(JobConstant.DefaultAdvert.NO, mongodbId);
        }
    }

    /**
     * 操作商铺
     */
    private void operateShop(HashMap<String, Object> msgMap) {
        Integer operateType = (Integer) msgMap.get(OPERATE_TYPE);
        String mongodbId = (String) msgMap.get(ADVERT_MONGODB_ID);
        if (operateType == JobConstant.OperateType.SHOP_TURN_OFF) {
            shopMongoDaoImpl.turnOnOrOff(0, mongodbId);
        } else if (operateType == JobConstant.OperateType.SHOP_TURN_ON) {
            shopMongoDaoImpl.turnOnOrOff(1, mongodbId);
        } else if (operateType == JobConstant.OperateType.SHOP_RECOMMEND_ON) {
            shopMongoDaoImpl.recommendOnOrOff(Constant.RecommendStatus.YES, mongodbId);
        } else if (operateType == JobConstant.OperateType.SHOP_RECOMMEND_OFF) {
            shopMongoDaoImpl.recommendOnOrOff(Constant.RecommendStatus.NO, mongodbId);
        }
    }
}
