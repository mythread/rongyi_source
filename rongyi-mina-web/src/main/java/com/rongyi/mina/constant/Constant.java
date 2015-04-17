package com.rongyi.mina.constant;

/**
 * @author jiejie 2014年4月9日 上午11:58:25
 */
public interface Constant {

    interface TaskStatus {

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
     * task类型：商铺，广告，品牌
     */
    interface TaskType {

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
     * 同步状态
     */
    interface SynchState {

        /**
         * 同步中
         */
        int SYNCHRONOUS_ING  = 1;
        /**
         * 同步完成
         */
        int SYNCHRONOUS_END  = 2;
        /**
         * 同步失败
         */
        int SYNCHRONOUS_FAIL = 3;
    }

    interface OpenState {

        /**
         * 开启
         */
        Byte OPEN_STATE  = 1;
        /**
         * 关闭
         */
        Byte CLOSE_STATE = 0;
    }

    interface RecommendStatus {

        int YES = 1;
        int NO  = 0;
    }
}
