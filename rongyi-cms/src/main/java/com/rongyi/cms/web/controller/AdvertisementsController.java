package com.rongyi.cms.web.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import base.config.PropertyConfigurer;
import base.util.character.JsonUtil;
import base.util.character.StringUtil;
import base.util.date.DateTool;
import base.util.file.FileUtil;
import base.util.file.ResizePicUtil;

import com.rongyi.cms.bean.AdZones;
import com.rongyi.cms.bean.Advertisements;
import com.rongyi.cms.bean.Shops;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.constant.JobConstant;
import com.rongyi.cms.service.AdZonesService;
import com.rongyi.cms.service.AdvertisementsService;
import com.rongyi.cms.service.JobService;
import com.rongyi.cms.service.ShopsService;

@Controller
@RequestMapping("/advertisements")
public class AdvertisementsController {

    private Logger                logger = Logger.getLogger(this.getClass());
    @Autowired
    private AdvertisementsService advertisementsService;
    @Autowired
    private AdZonesService        adZonesService;
    @Autowired
    private ShopsService          shopsService;
    @Autowired
    private JobService            jobService;
    @Autowired
    private PropertyConfigurer    propertyConfigurer;

    // 使用在异步JSON里面
    // private Map<String, Object> resultMap = new HashMap<String, Object>();
    // private Map<String,Object> paramsMap;

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
        logger.info("welcome list...");
        return "advertisements/list";
    }

    /**
     * 去新增或者修改的页面
     * 
     * @param request
     * @param adZoneId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(HttpServletRequest request, @RequestParam("adZoneId")
    String adZoneId, Integer adId, ModelMap modelMap) {
        try {
            logger.info("welcome edit... adZoneId>>>" + adZoneId);
            logger.info("welcome edit... adId>>>" + adId);
            modelMap.put("adZoneId", adZoneId);// 广告位ID
            String adZoneName = "";
            if (adZoneId != null && StringUtils.isNotBlank(adZoneId.trim())) {
                AdZones adZones = adZonesService.get(adZoneId);
                if (adZones != null) {
                    adZoneName = adZones.getName();
                }
            } else {
                // 异常情况
            }
            // 判断商家链接有无
            String shopUrl = "";
            /**
             * 判断是缩略图还是另张�� 将广告图缩略1 ,使用其他图片2
             */
            String hasMinPic = "1";
            String startTime = "";
            String endTime = "";
            // 获取广告内容
            if (adId != null && adId > 0) {
                Advertisements advertisements = advertisementsService.get(adId);
                if (advertisements != null) {
                    // 判断是否是已经有待同步内容的当前内容，如果是的话，要跳转到修改待同步内容
                    if (advertisements.getPid() == 0 && advertisements.getRecordType() == 0) {
                        // 当前内容，去查待同步内容
                        Advertisements sumAd = advertisementsService.selectByPid(advertisements.getId());
                        StringBuilder url = new StringBuilder();
                        url.append("redirect:").append("../advertisements/edit?adId=").append(sumAd.getId());
                        return url.toString();
                    }
                    modelMap.put("advertisements", advertisements);// 广告
                    // 商家名称
                    if (advertisements.getShopUrl() != null
                        && StringUtils.isNotBlank(advertisements.getShopUrl().trim())) {
                        // Shops shops = shopsService.get(Integer.valueOf(advertisements.getShopUrl()));
                        Shops shops = shopsService.selectByMongoId(advertisements.getShopUrl());
                        shopUrl = shops.getName();// 广告位名��
                    }
                    // 判断是缩略图还是另张��
                    String pic = advertisements.getPicture();
                    String minPic = advertisements.getMinPicture();
                    if (StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(minPic)) {
                        pic = pic.replace("original", "resize");
                        if (pic.equals(minPic)) {
                            hasMinPic = "1";
                        } else {
                            hasMinPic = "2";
                        }
                    }
                    startTime = DateTool.date2String(advertisements.getStartTime(), DateTool.FORMAT_DATE);
                    endTime = DateTool.date2String(advertisements.getEndTime(), DateTool.FORMAT_DATE);
                }

                modelMap.put("advertisements", advertisements);// 广告
                // 商家名称
                if (advertisements.getShopUrl() != null && StringUtils.isNotBlank(advertisements.getShopUrl().trim())) {
                    Shops shops = shopsService.selectByMongoId(advertisements.getShopUrl());
                    shopUrl = shops.getName();// 广告位名��
                }
                // 判断是缩略图还是另张��
                String pic = advertisements.getPicture();
                String minPic = advertisements.getMinPicture();
                if (StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(minPic)) {
                    pic = pic.replace("original", "resize");
                    if (pic.equals(minPic)) {
                        hasMinPic = "1";
                    } else {
                        hasMinPic = "2";
                    }
                }
                startTime = DateTool.date2String(advertisements.getStartTime(), DateTool.FORMAT_DATE);
                endTime = DateTool.date2String(advertisements.getEndTime(), DateTool.FORMAT_DATE);
            }

            modelMap.put("startTime", startTime);//
            modelMap.put("endTime", endTime);//
            modelMap.put("shopName", shopUrl);// 商家名称
            modelMap.put("adZoneName", adZoneName);// 广告位名��
            modelMap.put("hasMinPic", hasMinPic);// 判断是缩略图还是另张��

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "advertisements/edit";
    }

    /**
     * 保存广告
     * 
     * @param form
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public void save(@RequestParam("paramsJson")
    String paramsJson, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramsMap;
        try {
            logger.info("welcome save...paramsJson>>>" + paramsJson);
            resultMap = new HashMap<String, Object>();
            if (paramsJson == null) {
                resultMap.put("msg", "参数为NULL，请关闭再重试！");
                resultMap.put("status", 0);
                return;
            }
            resultMap.put("status", 0);
            resultMap.put("msg", "系统出错，请重试");
            paramsMap = JsonUtil.getMapFromJson(paramsJson);
            String adZoneId = (String) paramsMap.get("adZoneId");
            // if(form == null) {
            // throw new Exception("系统出错");
            // }
            if (adZoneId == null || StringUtils.isBlank(adZoneId.trim())) {
                throw new Exception("系统出错");
            }
            String adName = (String) paramsMap.get("adName");
            if (adName == null || StringUtils.isBlank(adName.trim())) {
                throw new Exception("请输入名称！");
            } else {
                if (StringUtil.stringLength(adName) > 30) {
                    throw new Exception("名称不可超过30个字符！");
                }
            }
            String startTime = (String) paramsMap.get("startTime");
            if (startTime == null || StringUtils.isBlank(startTime.trim())) {
                throw new Exception("请输入开始时间！");
            }
            String endTime = (String) paramsMap.get("endTime");
            if (endTime == null || StringUtils.isBlank(endTime.trim())) {
                throw new Exception("请输入结束时间！");
            }
            if (DateTool.compareDate(startTime, endTime) > 0) {
                throw new Exception("开始日期不能大于结束日期!");
            }
            // String defaultAd = (String) paramsMap.get("defaultAd");
            String onStatus = (String) paramsMap.get("onStatus");
            int adId_ = 0;
            int adPid_ = 0;
            String adId = (String) paramsMap.get("adId");
            if (adId != null && StringUtils.isNotBlank(adId.trim())) {
                adId_ = Integer.parseInt(adId);
                Advertisements tmp = advertisementsService.get(adId_);
                if (tmp != null && tmp.getPid() != null && tmp.getPid().intValue() > 0) {
                    adPid_ = tmp.getPid();
                }
            }
            List<Advertisements> list = advertisementsService.queryIncludeDate(startTime, endTime, adId_, adPid_,
                                                                               adZoneId);
            if (list != null && list.size() > 0) {
                throw new Exception("您设定的日期中，该广告位已有预定投放!");
            }
            String picture = (String) paramsMap.get("picture");
            if (picture == null || StringUtils.isBlank(picture.trim())) {
                throw new Exception("请上传广告素材！");
            } else {
                if (picture.indexOf("http") > -1) {
                    String localPath = propertyConfigurer.getProperty(Constant.Common.IMAGE_ROOT_DIR) + File.separator;
                    localPath = localPath + Constant.PicPath.advertisements + File.separator
                                + Constant.PicPath.original + File.separator;
                    picture = FileUtil.download(picture, localPath);
                }
            }
            // 无链��商家页面1
            String hasLink = (String) paramsMap.get("hasLink");
            String shopUrl = (String) paramsMap.get("shopUrl");
            if ("1".equals(hasLink)) {
                if (shopUrl == null || StringUtils.isBlank(shopUrl.trim())) {
                    throw new Exception("请输入链接地址");
                }
            } else {
                shopUrl = "";
            }
            // 将广告图缩略1 使用其他图片2
            String hasMinPic = (String) paramsMap.get("hasMinPic");
            String minPicture = (String) paramsMap.get("minPicture");
            if ("2".equals(hasMinPic)) {
                if (minPicture == null || StringUtils.isBlank(minPicture.trim())) {
                    throw new Exception("请上传下方缩略图");
                }
            } else {
                minPicture = "";
            }

            // 处理缩略��
            // String fromFileStr = picture;
            String minPic = minPicture;
            if (picture != null && StringUtils.isNotBlank(picture.trim()) && "1".equals(hasMinPic)) {
                minPic = picture;// fromFileStr.replace("original", "resize");
                // String rootPath = request.getSession().getServletContext().getRealPath(File.separator +
                // Constant.PicPath.rootPath + File.separator);
                String rootPath = propertyConfigurer.getProperty(Constant.Common.IMAGE_ROOT_DIR) + File.separator;
                String fileName = picture;// .substring(fromFileStr.lastIndexOf("/")
                                          // + 1, fromFileStr.length());
                String fromFilePath = rootPath + File.separator + Constant.PicPath.advertisements + File.separator
                                      + Constant.PicPath.original + File.separator + fileName;
                // fromFileStr = realPath + File.separator + fileName;
                String saveToFilePath = rootPath + File.separator + Constant.PicPath.advertisements + File.separator
                                        + Constant.PicPath.resize + File.separator + fileName;
                logger.info("fromFilePath>>>" + fromFilePath);
                logger.info("saveToFilePath>>>" + saveToFilePath);
                ResizePicUtil.saveImageAsJpg(fromFilePath, saveToFilePath, 100, 100, true);
            }/*
              * else if(minPicture != null && StringUtils.isNotBlank(minPicture) && "2".equals(form.getHasMinPic())) {
              * fromFileStr = minPicture; minPic = fromFileStr.replace("original", "resize"); String realPath =
              * request.getSession().getServletContext().getRealPath( "/upload/original"); String fileName =
              * fromFileStr.substring(fromFileStr.lastIndexOf("/") + 1, fromFileStr.length()); fromFileStr = realPath +
              * File.separator + fileName; String saveToFileStr = fromFileStr.replace("original", "resize");
              * ResizePicUtil.saveImageAsJpg(fromFileStr, saveToFileStr, 0, 0, false); }
              */
            Advertisements ad = null;
            if (adId != null && StringUtils.isNotBlank(adId.trim())) {
                ad = advertisementsService.get(Integer.parseInt(adId));
            } else {
                ad = new Advertisements();
            }
            if (ad != null) {
                ad.setEndTime(DateTool.string2Date(endTime, DateTool.FORMAT_DATE));
                ad.setStartTime(DateTool.string2Date(startTime, DateTool.FORMAT_DATE));
                ad.setName(adName);
                ad.setShopUrl(shopUrl);
                if (minPic != null && StringUtils.isNotBlank(minPic.trim())) {
                    ad.setMinPicture(minPic);
                }
                if (picture != null && StringUtils.isNotBlank(picture.trim())) {
                    ad.setPicture(picture);
                }
                ad.setAdZoneId(adZoneId);
                ad.setOnStatus(Byte.parseByte(onStatus));
                // ad.setDefaultPicture(Integer.parseInt(defaultAd));
                int id = advertisementsService.save(ad);
                // 插入JOB�
                jobService.insertJob(JobConstant.JobType.ADVERT_TYPE, String.valueOf(id),
                                     JobConstant.OperateType.NEED_REVIEW);
                resultMap.put("status", 1);
                resultMap.put("msg", "ok");
                resultMap.put("adId", id);
                resultMap.put("adZoneId", adZoneId.trim());
            } else {
                // 异常
                resultMap.put("status", 0);
                resultMap.put("msg", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", 0);
            resultMap.put("msg", e.getMessage());
        } finally {
            String json = JsonUtil.getJSONString(resultMap);
            System.out.println(json);
            responseJson(response, json);
        }
        return;
    }

    /**
     * 查询商家 2014.4.3 lim
     * 
     * @param q
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/searchShops")
    public void searchShops(String q, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (q != null) {
            q = URLDecoder.decode(q, "UTF-8");
        }
        logger.info("welcome searchShops..." + q);
        String json = shopsService.queryLikeName(q);
        logger.info(json);
        responseJson(response, json);
        return;
    }

    /**
     * 验证开始结束时�� 2014.4.3 lim
     * 
     * @param startTime
     * @param endTime
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/verifyDate")
    public void verifyDate(String startTime, String endTime, String adId, String adZoneId, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        logger.info("welcome verifyDate...");
        logger.info("startTime>>>" + startTime);
        logger.info("endTime>>>" + endTime);
        try {
            if (startTime != null && StringUtils.isNotBlank(startTime.trim()) && endTime != null
                && StringUtils.isNotBlank(endTime.trim())) {
                startTime = startTime.trim();
                endTime = endTime.trim();
                if (DateTool.compareDate(startTime, endTime) > 0) {
                    throw new Exception("开始日期不能大于结束日期!");
                }
                int adId_ = 0;
                if (adId != null && StringUtils.isNotBlank(adId.trim())) {
                    adId_ = Integer.parseInt(adId.trim());
                }
                int adPid_ = 0;
                if (adId_ > 0) {
                    Advertisements tmp = advertisementsService.get(adId_);
                    if (tmp != null && tmp.getPid() != null && tmp.getPid().intValue() > 0) {
                        adPid_ = tmp.getPid();
                    }
                }
                if (adZoneId == null || StringUtils.isBlank(adZoneId.trim())) {
                    throw new Exception("系统出错");
                }
                List<Advertisements> list = advertisementsService.queryIncludeDate(startTime, endTime, adId_, adPid_,
                                                                                   adZoneId);
                if (list != null && list.size() > 0) {
                    resultMap.put("data", list);
                    resultMap.put("status", 0);
                    resultMap.put("msg", "您设定的日期中，该广告位已有预定投放!");
                } else {
                    resultMap.put("status", 1);
                    resultMap.put("msg", "ok");
                }
            } else {
                resultMap.put("status", 0);
                resultMap.put("msg", "error");
            }
            // String json = JsonUtil.getJSONString(resultMap);
            // responseJson(response, json);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", 0);
            resultMap.put("msg", e.getMessage());
        } finally {
            String json = JsonUtil.getJSONString(resultMap);
            System.out.println(json);
            responseJson(response, json);
        }
        return;
    }

    /**
     * 向客户端输出指定格式的文��
     * 
     * @param response 响应
     * @param text 要发的内��
     * @param contentType 发送的格式
     */
    public void doPrint(HttpServletResponse response, String text, String contentType) {
        try {
            response.setContentType(contentType);
            response.getWriter().write(text);
        } catch (IOException e) {
            System.out.println("向客户端输出时发生异�" + e.getMessage());
        }
    }

    /**
     * 直接输出json
     */
    public void responseJson(HttpServletResponse response, String json) {
        doPrint(response, json, "application/json;charset=UTF-8");
    }

    /**
     * 查看广告信息
     * 
     * @param request
     * @param adZoneId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/view")
    public String view(HttpServletRequest request, @RequestParam("adZoneId")
    String adZoneId, Integer adId, ModelMap modelMap) {
        try {
            logger.info("welcome view... adZoneId>>>" + adZoneId);
            logger.info("welcome view... adId>>>" + adId);
            modelMap.put("adZoneId", adZoneId);// 广告位ID
            String adZoneName = "";
            if (adZoneId != null && StringUtils.isNotBlank(adZoneId.trim())) {
                AdZones adZones = adZonesService.get(adZoneId);
                if (adZones != null) {
                    adZoneName = adZones.getName();
                }
            } else {
                // 异常情况
            }
            // 判断商家链接有无
            String shopUrl = "";
            /**
             * 判断是缩略图还是另张�� 将广告图缩略1 ,使用其他图片2
             */
            String hasMinPic = "1";
            String startTime = "";
            String endTime = "";
            // 获取广告内容
            if (adId != null && adId > 0) {
                Advertisements advertisements = advertisementsService.get(adId);
                if (advertisements != null) {
                    // 待同步内容还是当前内容（审核通过的）
                    /*
                     * if(advertisements.getPid()==-1 && advertisements.getRecordType()==1) { //只有一条待同步内容 }else
                     * if(advertisements.getPid()==-1 && advertisements.getRecordType()==0) { //修改已审核通过的记录（只有一条审核通过的记录）
                     * }else if(advertisements.getPid()>0 && advertisements.getRecordType()==1) { //修改有审核通过记录的草记录
                     * advertisements = advertisementsService.get(advertisements.getPid());
                     * //advertisements:pid=0,recordType=0 }
                     */
                    if (advertisements.getPid() == 0 && advertisements.getRecordType() == 0) {
                        // 当前内容，去查待同步内容
                        Advertisements sumAd = advertisementsService.selectByPid(advertisements.getId());
                        modelMap.put("sumAdId", sumAd.getId());//
                    }
                    modelMap.put("advertisements", advertisements);// 广告
                    // 商家名称
                    if (advertisements.getShopUrl() != null
                        && StringUtils.isNotBlank(advertisements.getShopUrl().trim())) {
                        // Shops shops = shopsService.get(Integer.valueOf(advertisements.getShopUrl()));
                        Shops shops = shopsService.selectByMongoId(advertisements.getShopUrl());
                        shopUrl = shops.getName();// 广告位名
                    }
                    // 判断是缩略图还是另张
                    String pic = advertisements.getPicture();
                    String minPic = advertisements.getMinPicture();
                    if (StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(minPic)) {
                        pic = pic.replace("original", "resize");
                        if (pic.equals(minPic)) {
                            hasMinPic = "1";
                        } else {
                            hasMinPic = "2";
                        }
                    }
                    startTime = DateTool.date2String(advertisements.getStartTime(), DateTool.FORMAT_DATE);
                    endTime = DateTool.date2String(advertisements.getEndTime(), DateTool.FORMAT_DATE);
                }

                modelMap.put("advertisements", advertisements);// 广告
                // 商家名称
                if (advertisements.getShopUrl() != null && StringUtils.isNotBlank(advertisements.getShopUrl().trim())) {
                    Shops shops = shopsService.selectByMongoId(advertisements.getShopUrl());
                    shopUrl = shops.getName();// 广告位名
                }
                // 判断是缩略图还是另张
                String pic = advertisements.getPicture();
                String minPic = advertisements.getMinPicture();
                if (StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(minPic)) {
                    pic = pic.replace("original", "resize");
                    if (pic.equals(minPic)) {
                        hasMinPic = "1";
                    } else {
                        hasMinPic = "2";
                    }
                }
                startTime = DateTool.date2String(advertisements.getStartTime(), DateTool.FORMAT_DATE);
                endTime = DateTool.date2String(advertisements.getEndTime(), DateTool.FORMAT_DATE);
            }

            modelMap.put("startTime", startTime);//
            modelMap.put("endTime", endTime);//
            modelMap.put("shopName", shopUrl);// 商家名称
            modelMap.put("adZoneName", adZoneName);// 广告位名��
            modelMap.put("hasMinPic", hasMinPic);// 判断是缩略图还是另张��

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "advertisements/view";
    }
}
