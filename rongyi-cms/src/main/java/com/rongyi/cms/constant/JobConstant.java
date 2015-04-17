package com.rongyi.cms.constant;

/**
 * 处理job相关的常量信息
 * 
 * @author jiejie 2014年4月8日 上午11:25:45
 */
public interface JobConstant {

    interface JobStatus {

        /**
         * 未处理
         */
        int UN_HANDLE  = 0;

        /**
         * 已处理
         */
        int END_HANDLE = 1;
    }

    interface ActionType {

        /**
         * 新增
         */
        int ADD_ACTION    = 0;

        /**
         * 更新
         */
        int UPDATE_ACTION = 1;
    }

    /**
     * job类型：商铺，广告，品牌
     */
    interface JobType {

        /**
         * 商铺
         */
        int SHOP_TYPE   = 0;

        /**
         * 广告
         */
        int ADVERT_TYPE = 1;

        /**
         * 品牌
         */
        int BRAND_TYPE  = 2;

    }

    /**
     * job 不审核时向mongo中的操作类型
     */
    interface OperateType {

        /**
         * crm中需要审核
         */
        int NEED_REVIEW = 0;

        /**
         * 广告开启
         */
        int AD_TURN_ON  = 1;

        /**
         * 广告关闭
         */
        int AD_TURN_OFF = 2;

        /**
         * 广告删除
         */
        int AD_DELETE   = 3;
        
        /**
         * 不是默认广告
         */
        int AD_DEFAULT_OFF   = 4;
        
        /**
         * 是默认广告
         */
        int AD_DEFAULT_ON   = 5;
        /**
         * 店铺关闭
         */
        int SHOP_TURN_OFF =6;
        /**
         * 店铺开启
         */
        int SHOP_TURN_ON =7;
        
        /**
         * 店铺不推荐
         */
        int SHOP_RECOMMEND_OFF =8;
        
        /**
         * 店铺推荐
         */
        int SHOP_RECOMMEND_ON= 9;
    }
}
