package com.rongyi.cms.constant;

public interface Constant {

    /**
     * param常量表biztype
     */
    interface Paramtable {

        /**
         * 商场信息
         */
        String PARAM_TABLE_BIZTYPE_MALL  = "1"; // 商场ID
        /**
         * 商场楼层信息
         */
        String PARAM_TABLE_BIZTYPE_BUILD = "2"; // 楼层
    }

    /**
     * 共同类型
     */
    interface Common {

        int    YES                  = 1;
        int    NO                   = 0;
        // 图片目录
        String IMAGE_ROOT_DIR       = "image.root.dir";
        // CRM mina服务器的IP和端口
        String MINA_SERVER_IP       = "mina.server.ip";
        String MINA_SERVER_PORT     = "mina.server.port";

        // CMS mina server端port
        String CMS_MINA_SERVER_PORT = "cms.mina.server.port";
        // 商场名称和图片
        String HEADER_MALL_NAME     = "header.mall.name";
        String MALL_SHOP_IMG        = "mall.shop.img";
    }

    /**
     * 开启状态
     */
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

    /**
     * 删除状态
     */
    interface DeleteState {

        /**
         * 未删除
         */
        Byte DELETE_NO  = 0;

        /**
         * 已经删除
         */
        Byte DELETE_YES = 1;
    }

    /**
     * photo表OwnerType
     */
    interface Photo {

        String OwnerType1 = "Shop";
    }

    /**
     * 图片路径
     * 
     * @author lim
     */
    interface PicPath {

        /**
         * 图片根目录 upload
         */
        String rootPath       = "/upload";
        /**
         * 广告图片目录 advertise
         */
        String advertisements = "advertise";
        /**
         * 原图 original
         */
        String original       = "original";
        /**
         * 缩略图 resize
         */
        String resize         = "resize";
        /**
         * 商家图片目录
         */
        String shops          = "shops";
    }
}
