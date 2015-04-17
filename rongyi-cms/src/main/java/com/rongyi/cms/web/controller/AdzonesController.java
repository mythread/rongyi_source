package com.rongyi.cms.web.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import base.util.date.DateTool;

import com.rongyi.cms.bean.AdZones;
import com.rongyi.cms.bean.Advertisements;
import com.rongyi.cms.constant.JobConstant;
import com.rongyi.cms.service.AdZonesService;
import com.rongyi.cms.service.AdvertisementsService;
import com.rongyi.cms.service.JobService;

@Controller
@RequestMapping("/adzones")
public class AdzonesController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AdZonesService adZonesService;
	@Autowired
	private AdvertisementsService advertisementsService;
	@Autowired
	private JobService jobService;

	/**
	 * 广告位首页
	 * 
	 * @author Fuxinwei
	 * */
	@RequestMapping(value = "/index")
	public String viewAdZones(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		try{
			List<AdZones> adZonesList = adZonesService.getAll();
			modelMap.put("adzonesList", adZonesList);
			
		}catch(Exception e){
			logger.error(e);
		}
		return "adzones/index";
	}

	/**
	 * 广告列表初始页面
	 * 
	 * @param adZoneId:广告位id
	 * @author Fuxinwei
	 * */
	@RequestMapping(value = "/adlist")
	public String listAdByAdZonesId(
			HttpServletRequest request,
			ModelMap modelMap,
			@RequestParam("adZoneId") String adZoneId) {
		Map<String, Integer> adSynchMap = geyDefaultSynchMap();
		try{
			adSynchMap = advertisementsService.getSynchStatusByAdZoneId(adZoneId);
			String startTime = DateTool.getCurrentDateYY_MM_DD();
			modelMap.put("adSynchMap", adSynchMap);
			modelMap.put("adZoneId", adZoneId);
			modelMap.put("searchStartTime", startTime);
		}catch(Exception e){
			logger.error(e);
		}
		
		return "adzones/adlist";

	}

	/**
	 * 广告列表AJAX请求
	 * 
	 *  @param adZoneId:广告位id,synchStatus:审核状态,onStatus：开启状态
	 * 
	 * 
	 * @author Fuxinwei
	 * */
	@RequestMapping(value = "/ajaxadlist")
	public String listAdByAdZonesIdAjax(
			HttpServletRequest request,
			ModelMap modelMap,
			@RequestParam("adZoneId") String adZoneId,
			@RequestParam(value = "synchStatus", defaultValue = "") String synchStatus,
			@RequestParam(value = "onStatus", defaultValue = "") String onStatus,
			String startTime, String endTime) {
		
		List<Advertisements> adList = new ArrayList<Advertisements>();
		Map<String, Integer> adSynchMap =  geyDefaultSynchMap();
		try{
			Advertisements searchParam = new Advertisements();
			searchParam.setAdZoneId(adZoneId);
			if (!StringUtils.isBlank(startTime)) {
				startTime = URLDecoder.decode(startTime, "UTF-8");
			} else {
				startTime = null;
			}
			if (!StringUtils.isBlank(endTime)) {
				endTime = URLDecoder.decode(endTime, "UTF-8");
			} else {
				endTime = null;
			}
			logger.info("startTime>>>" + startTime);
			logger.info("endTime>>>" + endTime);

			if (!StringUtils.isBlank(synchStatus)) {
				adList = advertisementsService.selectAdListByMap(adZoneId,
						 Integer.valueOf(synchStatus), startTime, endTime);		
			} else if (!StringUtils.isBlank(onStatus)) {
				adList = advertisementsService.selectOnOffStatusAdListByMap(adZoneId,
						 Integer.valueOf(onStatus),  startTime, endTime);
			} else if(StringUtils.isBlank(synchStatus)&&StringUtils.isBlank(onStatus)){
				adList = advertisementsService.selectAdListByMap(adZoneId,
						null, startTime, endTime);
			}	
			adSynchMap = advertisementsService.getSynchStatusByAdZoneId(adZoneId);
			modelMap.put("adZoneId", adZoneId);
			
		}catch(Exception e){
			logger.error(e);
		}

		modelMap.put("adList", adList);
		modelMap.put("adSynchMap", adSynchMap);
		return "adzones/ajaxadlist";
	}

	/**
	 * 删除广告投放
	 * 
	 * @author ShaoYanbin
	 * */
	@RequestMapping(value = "/deleteAd")
	public String deleteAdToInfo(
			HttpServletRequest request,
			ModelMap modelMap,
			@RequestParam("adZoneId") String adZoneId,
			@RequestParam("id") Integer adId,
			@RequestParam(value = "synchStatus", defaultValue = "-1") Integer synchStatus)
			throws Exception {

		AdZones adZones = null;
		Advertisements searchParam = new Advertisements();
		List<Advertisements> adList = null;
		Map<String, Integer> adSynchMap = null;
		advertisementsService.delete(adId);
		jobService.insertJob(JobConstant.JobType.ADVERT_TYPE,
				String.valueOf(adId), JobConstant.OperateType.AD_DELETE);

		adZones = adZonesService.get(adZoneId);

		searchParam.setAdZoneId(adZoneId);
		if (synchStatus != -1) {
			searchParam.setSynchStatus(synchStatus);
		}
		adList = advertisementsService.selectByAdZoneId(searchParam);
		adSynchMap = advertisementsService.getSynchStatusByAdZoneId(adZoneId);
		modelMap.put("adZones", adZones);
		modelMap.put("adList", adList);
		modelMap.put("adSynchMap", adSynchMap);
		modelMap.put("adZoneId", adZoneId);
		return "adzones/ajaxadlist";
	}

	/**
	 * 开启或关闭广告投放
	 * 
	 * @author ShaoYanbin
	 * */
	@ResponseBody
	@RequestMapping(value = "/openOrCloseAd")
	public String openOrCloseAdToInfo(HttpServletRequest request,
			ModelMap modelMap, @RequestParam("adZoneId") String adZoneId,
			@RequestParam("id") Integer adId,
			@RequestParam(value = "onStatus", defaultValue = "0") Byte onStatus)
			throws Exception {
		AdZones adZones = null;
		Advertisements ad = null;
		Advertisements oldad = null;
		Integer operateAdid= null;
		adZones = adZonesService.get(adZoneId);
		ad = advertisementsService.get(adId);
		
		if(ad.getPid()!=-1){
			oldad = advertisementsService.get(ad.getPid());
			
			oldad.setAdZoneId(adZoneId);
			oldad.setOnStatus(onStatus);
		}
		
		ad.setOnStatus(onStatus);
		ad.setAdZoneId(adZoneId);
		advertisementsService.openOrClose(ad);
		if(oldad!=null){
			advertisementsService.openOrClose(oldad);
			operateAdid = oldad.getId();
		}else{
			operateAdid = adId;
		}

		if (onStatus == 0) {
			jobService.insertJob(JobConstant.JobType.ADVERT_TYPE,
					String.valueOf(operateAdid), JobConstant.OperateType.AD_TURN_OFF);
		} else {
			jobService.insertJob(JobConstant.JobType.ADVERT_TYPE,
					String.valueOf(operateAdid), JobConstant.OperateType.AD_TURN_ON);		
		}
		return "success";
	}

	/**
	 * @author ShaoYanbin 设置默认广告
	 * */
	@ResponseBody
	@RequestMapping(value = "/defaultAd")
	public String defaultAd(HttpServletRequest request,
			@RequestParam("adZoneId") String adZoneId,
			@RequestParam("id") Integer adId,
			@RequestParam("defaultPicture") Integer defaultPicture)
			throws Exception {

		AdZones adZones = null;
		Advertisements ad = null;
		if (defaultPicture == 0) {
			Advertisements defauldAd = advertisementsService.getDefauldAd(
					adZoneId, 1);
			if (null != defauldAd) {

				return "error";
			}
		}
		adZones = adZonesService.get(adZoneId);
		ad = advertisementsService.get(adId);
		ad.setId(adId);
		ad.setAdZoneId(adZoneId);
		int defaultAd = advertisementsService.defaultAd(ad);
		if (defaultAd == 0) {
			return "error";
		}

		jobService.insertJob(JobConstant.JobType.ADVERT_TYPE,
				String.valueOf(adId), JobConstant.OperateType.AD_DEFAULT_ON);

		return "success";
	}
	

	private Map<String, Integer> geyDefaultSynchMap(){
		Map<String, Integer> adSynchMap = new HashMap<String,Integer>();
		adSynchMap.put("1",0);
		adSynchMap.put("2",0);
		adSynchMap.put("3",0);
		return adSynchMap;
		
	}
}
