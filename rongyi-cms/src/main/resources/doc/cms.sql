/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : cms

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-04-03 09:55:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for advertisements
-- ----------------------------
DROP TABLE IF EXISTS `advertisements`;
CREATE TABLE `advertisements` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `shop_url` varchar(32) DEFAULT NULL COMMENT '跳转连接 商铺id',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片名称 ',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `ad_zone_id` varchar(32) DEFAULT NULL COMMENT '广告位id',
  `delete_status` tinyint(4) DEFAULT NULL COMMENT '删除状态 0删除 1删除',
  `on_status` tinyint(4) DEFAULT NULL COMMENT '开启状态 1开启 0 关闭',
  `synch_status` int(3) DEFAULT NULL COMMENT '同步状态 1 同步中 2同步成功 3失败',
  `mongodb_id` varchar(32) DEFAULT NULL COMMENT 'mongodb广告id',
  `synch_msg` varchar(255) DEFAULT NULL COMMENT '同步失败信息',
  `pid` int(32) DEFAULT '-1' COMMENT '新增已审核的修改记录:已审核ID;已审核未修改记录:-1;已审核已修改:0',
  `record_type` tinyint(4) DEFAULT NULL COMMENT '修改记录:1;已审核通过:0',
  `min_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告详情';

-- ----------------------------
-- Table structure for ad_zones
-- ----------------------------
DROP TABLE IF EXISTS `ad_zones`;
CREATE TABLE `ad_zones` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '广告位名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `zone_owner_id` varchar(32) DEFAULT NULL COMMENT '属于id',
  `owner_type` tinyint(4) DEFAULT NULL COMMENT '所属关系 1 商城 0容易',
  `seq` int(4) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告位';

-- ----------------------------
-- Table structure for brands
-- ----------------------------
DROP TABLE IF EXISTS `brands`;
CREATE TABLE `brands` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `average_consumption` varchar(64) DEFAULT NULL COMMENT '平均客单价',
  `aliases` varchar(64) DEFAULT NULL COMMENT '别名',
  `cname` varchar(64) DEFAULT NULL COMMENT '中文名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `ename` varchar(100) DEFAULT NULL COMMENT '英文名',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标名',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `operator` varchar(255) DEFAULT NULL COMMENT '操作人',
  `tags` varchar(255) DEFAULT NULL COMMENT '标签',
  `telephone` varchar(32) DEFAULT NULL COMMENT '电话',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `old_code` int(11) DEFAULT NULL COMMENT '旧的code',
  `old_id` int(11) DEFAULT NULL COMMENT '旧的id',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类id',
  `syhch_status` int(4) DEFAULT NULL COMMENT '同步状态 1同步中 2已同步 3同步失败',
  `syhch_msg` varchar(255) DEFAULT NULL COMMENT '同步失败信息',
  `pid` int(32) DEFAULT NULL,
  `record_type` int(4) DEFAULT NULL COMMENT '修改记录:1;已审核通过:0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌';

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `app_show` int(11) DEFAULT NULL COMMENT '客户端是否展示',
  `name` varchar(32) DEFAULT NULL COMMENT '分类名称',
  `old_code` int(11) DEFAULT NULL COMMENT '旧的code',
  `old_id` int(11) DEFAULT NULL COMMENT '旧的id',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类';

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmtcreate` datetime DEFAULT NULL,
  `gmtmodified` datetime DEFAULT NULL,
  `type` int(4) DEFAULT NULL COMMENT '广告 1 店铺2 品牌 3',
  `job_status` int(4) DEFAULT NULL,
  `cms_id` varchar(32) DEFAULT NULL,
  `action_type` int(4) DEFAULT NULL COMMENT '1 新增 2修改 3删除 4启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='job 表';

-- ----------------------------
-- Table structure for param
-- ----------------------------
DROP TABLE IF EXISTS `param`;
CREATE TABLE `param` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `service_type` varchar(64) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `biz_type` varchar(64) DEFAULT NULL COMMENT '参数类型',
  `seq` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='常量表';

-- ----------------------------
-- Table structure for photos
-- ----------------------------
DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `file` varchar(255) DEFAULT NULL COMMENT '文件名',
  `wonder_id` varchar(32) DEFAULT NULL COMMENT '属于谁',
  `owner_type` varchar(32) DEFAULT NULL COMMENT '属于谁类型',
  `position` int(11) DEFAULT NULL COMMENT '位置',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片';

-- ----------------------------
-- Table structure for shops
-- ----------------------------
DROP TABLE IF EXISTS `shops`;
CREATE TABLE `shops` (
  `id` varchar(32) NOT NULL COMMENT '店铺id',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `average_consumption` varchar(64) DEFAULT NULL COMMENT '平均消费',
  `brand_id` varchar(32) DEFAULT NULL COMMENT '品牌id',
  `comment_count` int(11) DEFAULT NULL COMMENT '评论数',
  `coordinate` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  `door_coordinate` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL COMMENT '坐标',
  `name` varchar(64) DEFAULT NULL COMMENT '中文名',
  `old_code` int(11) DEFAULT NULL,
  `old_id` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL COMMENT '评分',
  `shop_number` int(11) DEFAULT NULL COMMENT '商铺号 重点',
  `shop_type` int(11) DEFAULT NULL COMMENT '商铺类型',
  `slug` varchar(32) DEFAULT NULL COMMENT '缩写',
  `subtitle` varchar(64) DEFAULT NULL COMMENT '副标题',
  `tags` varchar(255) DEFAULT NULL COMMENT '标签',
  `telephone` varchar(32) DEFAULT NULL COMMENT '电话',
  `terminal_position` varchar(64) DEFAULT NULL COMMENT '终端机位置',
  `terminal_shop` varchar(64) DEFAULT NULL COMMENT '终端机',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `zone_id` varchar(32) DEFAULT NULL COMMENT '楼层id',
  `on_status` tinyint(4) DEFAULT NULL COMMENT '开启关闭',
  `synch_status` int(3) DEFAULT NULL COMMENT '同步状态 1同步中 2同步成功 3失败',
  `synch_msg` varchar(255) DEFAULT NULL COMMENT 'shop同步返回信息',
  `pid` varchar(32) DEFAULT '-1' COMMENT '新增已审核的修改记录:已审核ID;已审核未修改记录:-1;已审核已修改:0',
  `record_type` int(4) DEFAULT NULL COMMENT '修改记录:1;已审核通过:0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '用名称',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `mall_id` varchar(32) DEFAULT NULL COMMENT '商城id',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
