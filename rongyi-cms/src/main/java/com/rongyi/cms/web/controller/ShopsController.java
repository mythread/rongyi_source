package com.rongyi.cms.web.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import base.page.Pagination;
import base.util.character.JsonUtil;
import base.util.character.StringUtil;

import com.rongyi.cms.bean.Brands;
import com.rongyi.cms.bean.Categories;
import com.rongyi.cms.bean.Param;
import com.rongyi.cms.bean.Photos;
import com.rongyi.cms.bean.Shops;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.constant.JobConstant;
import com.rongyi.cms.service.BrandsService;
import com.rongyi.cms.service.CategoriesService;
import com.rongyi.cms.service.JobService;
import com.rongyi.cms.service.ParamService;
import com.rongyi.cms.service.PhotosService;
import com.rongyi.cms.service.ShopsService;
import com.rongyi.cms.web.form.ShopForm;

@Controller
@RequestMapping("/shops")
public class ShopsController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ShopsService shopsService;
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private PhotosService photosService;
	@Autowired
	private BrandsService brandsService;
	@Autowired
	private JobService jobService;
	@Autowired
	private ParamService paramService;

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
		List<Categories> cateList = categoriesService.selectAllParentCategories();
		Map<String, Integer> synchStatusMap = shopsService.groupByShopsBySynchStatus();
		modelMap.put("cateList", cateList);
		modelMap.put("synchStatusMap", synchStatusMap);
		return "shops/list";
	}

	@RequestMapping(value = "/ajaxSearchlist")
	public String ajaxSearchlist(HttpServletRequest request, ModelMap modelMap, ShopForm shopForm, HttpServletResponse response) throws Exception {
		// String str = request.getAttribute("123").toString();
		// System.out.println(str);
		Shops shops = new Shops();
		Pagination pagination = new Pagination(request);

		shops.setPagination(pagination);
		if (!StringUtils.isBlank(shopForm.getCategoriesId())) {
			shops.setCategoriesId(shopForm.getCategoriesId());
		}
		if (!StringUtils.isBlank(shopForm.getSearchParams().trim())) {
			shops.setSearchParams(shopForm.getSearchParams().trim());
		}
		if (shopForm.getOnStatus() != null) {
			shops.setOnStatus(shopForm.getOnStatus());
		}
		if (shopForm.getRecommend() != null) {
			shops.setRecommend(shopForm.getRecommend());
		}

		List<Shops> shopList = shopsService.searchShopsByParam(shops);
		modelMap.put("shopList", shopList);
		modelMap.put("pagination", shops.getPagination());

		return "shops/search_ajaxlist";
	}

	@RequestMapping(value = "/ajaxSynchlist")
	public String ajaxSynchList(HttpServletRequest request, ModelMap modelMap, ShopForm shopForm) throws Exception {
		List<Integer> synchStatList = new ArrayList<Integer>();
		List<Shops> resultList = new ArrayList<Shops>();
		Pagination pagination = new Pagination(request);
		Shops shops = new Shops();
		String synchStatus = shopForm.getSynchStatus();
		if (!StringUtils.isBlank(synchStatus)) {
			String[] synchStatArry = synchStatus.split("#");
			for (int i = 0; i < synchStatArry.length; i++) {
				if (!StringUtils.isBlank(synchStatArry[i])) {
					synchStatList.add(Integer.valueOf(synchStatArry[i]));
				}
			}
		}

		if (synchStatList.size() >= 1) {
			shops.setSynchStatus1(synchStatList.get(0));
		}
		if (synchStatList.size() >= 2) {
			shops.setSynchStatus2(synchStatList.get(1));
		}
		if (synchStatList.size() == 3 || synchStatList.size() == 0) {
			shops.setSynchStatus1(1);
			shops.setSynchStatus2(2);
			shops.setSynchStatus3(3);
		}
		shops.setPagination(pagination);
		resultList = shopsService.searchShopsBySynchStatus(shops);
		modelMap.put("pagination", shops.getPagination());
		modelMap.put("shopList", resultList);
		modelMap.put("", resultList);
		return "shops/synch_ajaxlist";
	}

	/**
	 * 商家详情修改---去到修改页面
	 * 
	 * @param request
	 * @param shopsId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, @RequestParam("shopsId") String shopsId, ModelMap modelMap) throws Exception {
		try {
			logger.info("welcome edit... shopsId>>>" + shopsId);
			Shops shops = null;
			if (shopsId != null) {
				shops = shopsService.get(Integer.parseInt(shopsId));
				if (shops != null) {
					// 判断是否是已经有待同步内容的当前内容，如果是的话，要跳转到修改待同步内容
					Brands brands = brandsService.selectByPrimaryKey(shops.getBrandId());
					shops.setBrands(brands);
					if (shops.getPid() == 0 && shops.getRecordType() == 0) {
						// 当前内容，去查待同步内容
						Shops sumAd = shopsService.selectByPid(shops.getId());

						StringBuilder url = new StringBuilder();
						url.append("redirect:").append("../shops/edit?shopsId=").append(sumAd.getId());
						return url.toString();
					}
					List<Photos> list = photosService.selectByOwnerIdAndType(shops.getId(), Constant.Photo.OwnerType1);
					if (list != null && list.size() > 0) {
						if (list.get(0) != null) {
							Photos p = list.get(0);
							if (StringUtils.isBlank(p.getFileUrl()) || "-1".equals(p.getFileUrl())) {
								// ../upload/shops/original/
								modelMap.put("pic", Constant.PicPath.rootPath + "/" + Constant.PicPath.shops + "/" + Constant.PicPath.original + "/" + p.getFile());//
							} else {
								modelMap.put("pic", p.getFileUrl());//
							}
						}
					}
					if(shops.getBrandId()!=null && shops.getBrandId().intValue()>0) {
						Brands b = brandsService.selectByPrimaryKey(shops.getBrandId());
						if(b != null) {
							shops.setBrands(b);
						}
					}
					Param floor = paramService.getFloor(shops.getZoneId());
					modelMap.put("floor", floor);//
				} else {
					throw new Exception();
				}
			}
			modelMap.put("shops", shops);//
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		
		// 用来标示是否是可视化编辑
		modelMap.put("isedit", 1);
		
		return "shops/view";
	}

	/**
	 * 商家详情修改---去到修改页面
	 * 
	 * @param request
	 * @param shopsId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editV")
	public String editV(HttpServletRequest request, @RequestParam("shopsId") String shopsId, ModelMap modelMap) throws Exception {
		try {
			logger.info("welcome editV... shopsId>>>" + shopsId);
			Shops shops = null;
			if (shopsId != null) {
				shops = shopsService.get(Integer.parseInt(shopsId));
				if (shops != null) {
					// 判断是否是已经有待同步内容的当前内容，如果是的话，要跳转到修改待同步内容
					Brands brands = brandsService.selectByPrimaryKey(shops.getBrandId());
					shops.setBrands(brands);
					if (shops.getPid() == 0 && shops.getRecordType() == 0) {
						// 当前内容，去查待同步内容
						Shops sumAd = shopsService.selectByPid(shops.getId());

						StringBuilder url = new StringBuilder();
						url.append("redirect:").append("../shops/editV?shopsId=").append(sumAd.getId());
						return url.toString();
					}
					List<Photos> list = photosService.selectByOwnerIdAndType(shops.getId(), Constant.Photo.OwnerType1);
					if (list != null && list.size() > 0) {
						if (list.get(0) != null) {
							Photos p = list.get(0);
							if (StringUtils.isBlank(p.getFileUrl()) || "-1".equals(p.getFileUrl())) {
								// ../upload/shops/original/
								modelMap.put("pic", Constant.PicPath.rootPath + "/" + Constant.PicPath.shops + "/" + Constant.PicPath.original + "/" + p.getFile());//
							} else {
								modelMap.put("pic", p.getFileUrl());//
							}
						}
					}
					if(shops.getBrandId()!=null && shops.getBrandId().intValue()>0) {
						Brands b = brandsService.selectByPrimaryKey(shops.getBrandId());
						if(b != null) {
							shops.setBrands(b);
						}
					}
				} else {
					throw new Exception();
				}
			}
			modelMap.put("shops", shops);//
			//标签处理
			List<String> tagList = null;
			if(shops.getTags()!=null && !shops.getTags().trim().equals("")) {
				String tags = shops.getTags();
				String[] tagArr = tags.split(" ");
				tagList = new ArrayList<String>(tagArr.length);
				for(String s : tagArr) {
					tagList.add(s);
				}
			}
			modelMap.put("tagList", tagList);//
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		// System.out.println("\n\n\n\n=============================================================");
		return "shops/editV";
	}

	/**
	 * 加载信息
	 * 
	 * @param request
	 * @param shopsId
	 * @param response
	 */
	@RequestMapping(value = "/loadInfo")
	public void loadInfo(HttpServletRequest request, @RequestParam("paramsJson") String paramsJson, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap;
		try {
			logger.info("welcome loadInfo... paramsJson>>>" + paramsJson);
			Shops shops = null;
			if (paramsJson == null) {
				resultMap.put("msg", "参数为NULL，请关闭再重试！");
				resultMap.put("status", 0);
				return;
			}
			resultMap.put("status", 0);
			resultMap.put("msg", "系统出错，请重试");
			paramsMap = JsonUtil.getMapFromJson(paramsJson);
			String shopsId = (String) paramsMap.get("shopsId");
			if (shopsId != null && StringUtils.isNotBlank(shopsId.trim())) {
				shops = shopsService.get(Integer.parseInt(shopsId));
				if (shops != null) {
					resultMap.put("shops", shops);//
					Brands brands = brandsService.selectByPrimaryKey(shops.getBrandId());
					if (brands != null) {
						shops.setBrands(brands);
					}
					List<Photos> list = photosService.selectByOwnerIdAndType(shops.getId(), Constant.Photo.OwnerType1);
					int position = 0;
					String file = "", fileName = "";
					if (list != null && list.size() > 0) {
						for (Photos p : list) {
							position++;
							if (p != null) {
								/*
								 * if(p.getPosition()!=null &&
								 * p.getPosition().intValue()>0) {
								 * resultMap.put(
								 * "pic_"+p.getPosition().intValue(),
								 * p.getFile());
								 * resultMap.put("id_"+p.getPosition
								 * ().intValue(), p.getId()); }
								 */
								if (StringUtils.isBlank(p.getFileUrl()) || "-1".equals(p.getFileUrl())) {
									// ../upload/shops/original/
									file = Constant.PicPath.rootPath + "/" + Constant.PicPath.shops + "/" + Constant.PicPath.original + "/" + p.getFile();//
									fileName = p.getFile();
								} else {
									file = p.getFileUrl();//
									fileName = p.getFileUrl();
								}
								resultMap.put("pic_" + position, file);
								resultMap.put("id_" + position, p.getId());
								resultMap.put("fileName_" + position, fileName);
							}
						}
					}
					// resultMap.put("photos", list);
					for (int i = 0; i < 4; i++) {
						if (resultMap.get("id_" + (i + 1)) == null) {
							resultMap.put("pic_" + (i + 1), "");
							resultMap.put("id_" + (i + 1), "");
							resultMap.put("fileName_" + (i + 1), "");
						}
					}
					resultMap.put("status", 1);
					resultMap.put("msg", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	/**
	 * 保存商家基本信息
	 * 
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveBase")
	public void saveBaseInfo(@RequestParam("paramsJson") String paramsJson, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap;
		try {
			logger.info("welcome saveBaseInfo... paramsJson>>>" + paramsJson);
			if (paramsJson == null) {
				resultMap.put("msg", "参数为NULL，请关闭再重试！");
				resultMap.put("status", 0);
				return;
			}
			paramsMap = JsonUtil.getMapFromJson(paramsJson);

			String shopsId = (String) paramsMap.get("shopsId");
			if (shopsId == null || StringUtils.isBlank(shopsId.trim())) {
				throw new Exception("系统出错，请稍候重试！");
			} else {
				shopsId = shopsId.trim();
			}
			String shopsName = (String) paramsMap.get("shopsName");
			if (shopsName != null && StringUtils.isNotBlank(shopsName.trim())) {
				shopsName = URLDecoder.decode(shopsName, "UTF-8");
				if (StringUtil.stringLength(shopsName) > 60) {
					throw new Exception("名称不可超过60个字符！");
				}
			} else {
				throw new Exception("商户名称不可为空");
			}
			String shopsLogo = (String) paramsMap.get("shopsLogo");
			if (shopsLogo != null && StringUtils.isNotBlank(shopsLogo.trim())) {
				shopsLogo = URLDecoder.decode(shopsLogo, "UTF-8");
			} else {
				throw new Exception("品牌&logo不可为空");
			}
			String shopsTags = (String) paramsMap.get("shopsTags");
			if (shopsTags != null && StringUtils.isNotBlank(shopsTags.trim())) {
				shopsTags = URLDecoder.decode(shopsTags, "UTF-8");
				if (StringUtil.stringLength(shopsTags) > 200) {
					throw new Exception("商户标签不可超过200个字符！");
				}
			} else {
				throw new Exception("商户标签不可为空");
			}
			int brandId = Integer.parseInt(shopsLogo);
			Shops shops = shopsService.get(Integer.parseInt(shopsId));
			if (shops != null) {
				shops.setName(shopsName);
				shops.setTags(shopsTags);
				shops.setBrandId(brandId);
				Brands b = brandsService.selectByPrimaryKey(brandId);
				shops.setMongoBrandId(b.getMongoId()!=null ? b.getMongoId() : "-1");
				int shopsId_ = shopsService.save(shops);
				logger.info("shopsId_>>>"+shopsId_);
				// 插入JOB表
				jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(shopsId_), JobConstant.OperateType.NEED_REVIEW);
				if (b != null && b.getSyhchStatus() != null && b.getSyhchStatus().intValue() == Constant.SynchState.SYNCHRONOUS_ING) {
					// 插入JOB表
					jobService.insertJob(JobConstant.JobType.BRAND_TYPE, String.valueOf(brandId), JobConstant.OperateType.NEED_REVIEW);
				}
			} else {
				throw new Exception("找不到记录，系统出错");
			}

			resultMap.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("msg", e.getMessage());
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	/**
	 * 保存商家选填信息
	 * 
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOptional")
	public void saveOptionalInfo(@RequestParam("paramsJson") String paramsJson, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap;
		try {
			logger.info("welcome saveOptionalInfo... paramsJson>>>" + paramsJson);
			if (paramsJson == null) {
				resultMap.put("msg", "参数为NULL，请关闭再重试！");
				resultMap.put("status", 0);
				return;
			}
			paramsMap = JsonUtil.getMapFromJson(paramsJson);

			String shopsId = (String) paramsMap.get("shopsId");
			if (shopsId == null || StringUtils.isBlank(shopsId.trim())) {
				throw new Exception("系统出错，请稍候重试！");
			} else {
				shopsId = shopsId.trim();
			}

			Shops shops = shopsService.get(Integer.parseInt(shopsId));
			if (shops != null) {
				String openTime = (String) paramsMap.get("openTime");
				if (openTime != null && StringUtils.isNotBlank(openTime.trim())) {
					openTime = URLDecoder.decode(openTime, "UTF-8");
					if (StringUtil.stringLength(openTime) > 30) {
						throw new Exception("营业时间不可超过30个字符！");
					}
					shops.setOpenTime(openTime);
				}
				String telephone = (String) paramsMap.get("telephone");
				if (telephone != null && StringUtils.isNotBlank(telephone.trim())) {
					telephone = URLDecoder.decode(telephone, "UTF-8");
					if (StringUtil.stringLength(telephone) > 30) {
						throw new Exception("商家电话不可超过30个字符！");
					}
					shops.setTelephone(telephone);
				}
				String description = (String) paramsMap.get("description");
				if (description != null && StringUtils.isNotBlank(description.trim())) {
					description = URLDecoder.decode(description, "UTF-8");
					if (StringUtil.stringLength(description) > 3000) {
						throw new Exception("商家简介不可超过3000个字符！");
					}
					shops.setDescription(description);
				}
				String shopsTags = (String) paramsMap.get("shopsTags");
				if (shopsTags != null && StringUtils.isNotBlank(shopsTags.trim())) {
					shopsTags = URLDecoder.decode(shopsTags, "UTF-8");
					if (StringUtil.stringLength(shopsTags) > 200) {
						throw new Exception("商户标签不可超过200个字符！");
					}
					shops.setTags(shopsTags);
				}
				String recommend = (String) paramsMap.get("recommend");
				if (recommend != null && StringUtils.isNotBlank(recommend)) {
					int recommend_ = Integer.parseInt(recommend);
					shops.setRecommend(recommend_);
				}
				int shopsId_ = shopsService.save(shops);
				// 插入JOB表
				jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(shopsId_), JobConstant.OperateType.NEED_REVIEW);
				Brands b = brandsService.selectByPrimaryKey(shops.getBrandId());
				if (b != null && b.getSyhchStatus() != null && b.getSyhchStatus().intValue() == Constant.SynchState.SYNCHRONOUS_ING) {
					// 插入JOB表
					jobService.insertJob(JobConstant.JobType.BRAND_TYPE, String.valueOf(shops.getBrandId()), JobConstant.OperateType.NEED_REVIEW);
				}
			} else {
				throw new Exception("找不到记录，系统出错");
			}

			resultMap.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("msg", e.getMessage());
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	/**
	 * 保存商家选填信息
	 * 
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDesc")
	public void saveDesc(@RequestParam("paramsJson") String paramsJson, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap;
		try {
			logger.info("welcome saveDesc... paramsJson>>>" + paramsJson);
			if (paramsJson == null) {
				resultMap.put("msg", "参数为NULL，请关闭再重试！");
				resultMap.put("status", 0);
				return;
			}
			paramsMap = JsonUtil.getMapFromJson(paramsJson);

			String shopsId = (String) paramsMap.get("shopsId");
			if (shopsId == null || StringUtils.isBlank(shopsId.trim())) {
				throw new Exception("系统出错，请稍候重试！");
			} else {
				shopsId = shopsId.trim();
			}
			String description = (String) paramsMap.get("description");
			if (description != null && StringUtils.isNotBlank(description.trim())) {
				description = URLDecoder.decode(description, "UTF-8");
				if (StringUtil.stringLength(description) > 3000) {
					throw new Exception("商家简介不可超过3000个字符！");
				}
			} else {
				throw new Exception("商家简介不可为空");
			}
			Shops shops = shopsService.get(Integer.parseInt(shopsId));
			if (shops != null) {
				shops.setDescription(description);
				int shopsId_ = shopsService.save(shops);
				// 插入JOB表
				jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(shopsId_), JobConstant.OperateType.NEED_REVIEW);
				Brands b = brandsService.selectByPrimaryKey(shops.getBrandId());
				if (b != null && b.getSyhchStatus() != null && b.getSyhchStatus().intValue() == Constant.SynchState.SYNCHRONOUS_ING) {
					// 插入JOB表
					jobService.insertJob(JobConstant.JobType.BRAND_TYPE, String.valueOf(shops.getBrandId()), JobConstant.OperateType.NEED_REVIEW);
				}
			} else {
				throw new Exception("找不到记录，系统出错");
			}

			resultMap.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("msg", e.getMessage());
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	/**
	 * 保存商家图片
	 * 
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePic")
	public void savePic(@RequestParam("paramsJson") String paramsJson, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap;
		try {
			logger.info("welcome savePic... paramsJson>>>" + paramsJson);
			if (paramsJson == null) {
				resultMap.put("msg", "参数为NULL，请关闭再重试！");
				resultMap.put("status", 0);
				return;
			}
			paramsMap = JsonUtil.getMapFromJson(paramsJson);

			String shopsId = (String) paramsMap.get("shopsId");
			int shopsId_ = 0;
			if (shopsId == null || StringUtils.isBlank(shopsId.trim())) {
				throw new Exception("系统出错，请稍候重试！");
			} else {
				shopsId = shopsId.trim();
				shopsId_ = Integer.parseInt(shopsId);
			}
			Shops shops = shopsService.get(shopsId_);
			int newShopsId = 0;
			if (shops != null) {
				newShopsId = shopsService.save(shops);
			} else {
				throw new Exception("找不到记录，系统出错");
			}
			String[] pics = { "shopsPic_1", "shopsPic_2", "shopsPic_3", "shopsPic_4" };
			String[] ids = { "shopsId_1", "shopsId_2", "shopsId_3", "shopsId_4" };
			String[] isUpdates = { "isUpdate_1", "isUpdate_2", "isUpdate_3", "isUpdate_4" };

			String shopsPic = null;
			int i = 0;
			int position = 0;
			Photos photo = null;
			int index = 0;
			String photoId = null;
			String isUpdated = null;
			for (String pic : pics) {
				position++;
				shopsPic = (String) paramsMap.get(pic);
				isUpdated = (String) paramsMap.get(isUpdates[index]);
				photoId = (String) paramsMap.get(ids[index]);
				if (shopsPic != null && StringUtils.isNotBlank(shopsPic.trim())) {
					i++;
					shopsPic = shopsPic.trim();
					if (StringUtil.stringLength(shopsPic) > 200) {
						throw new Exception("图片名称过长！");
					}
					if (isUpdated != null && "updated".equals(isUpdated)) {
						if (photoId != null && StringUtils.isNotBlank(photoId.trim())) {
							photo = photosService.get(Integer.parseInt(photoId));
						}
						if (photo == null) {
							photo = new Photos();
							photo.setCreatedAt(new Date());
							photo.setOwnerType(Constant.Photo.OwnerType1);
							photo.setPosition(position);
							photo.setOwnerId(newShopsId);
							photo.setOwnerMongoId(shops.getMongoId());
							photo.setDeleteStatus((byte)Constant.Common.NO);
						}
						photo.setFile(shopsPic);
						photo.setUpdatedAt(new Date());
						photo.setFileUrl("-1");
						photosService.save(photo);
					}
				}else if("delete".equals(isUpdated)){
					//逻辑删除照片,把图片名字清空掉
					if (photoId != null && StringUtils.isNotBlank(photoId.trim())) {
						Photos photoDel = new Photos();
						photoDel.setId(Integer.parseInt(photoId));
						photoDel.setDeleteStatus((byte)Constant.Common.YES);
						photosService.updateByPrimaryKeySelective(photoDel);
					}
				}
				index++;
			}
			if (i == 0) {
				throw new Exception("请上传图片！");
			}
			// 插入JOB表
			jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(newShopsId), JobConstant.OperateType.NEED_REVIEW);
			resultMap.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("msg", e.getMessage());
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	/**
	 * 向客户端输出指定格式的文�
	 * 
	 * @param response
	 *            响应
	 * @param text
	 *            要发的内�
	 * @param contentType
	 *            发送的格式
	 */
	public void doPrint(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			System.out.println("向客户端输出时发生异常  " + e.getMessage());
		}
	}

	/**
	 * 直接输出json
	 */
	public void responseJson(HttpServletResponse response, String json) {
		doPrint(response, json, "application/json;charset=UTF-8");
	}

	/**
	 * @author ShaoYanbin 商铺启用
	 * */
	@ResponseBody
	@RequestMapping(value = "/openOrCloseShop")
	public String openOrCloseShop(HttpServletRequest request, @RequestParam("id") Integer shop_id, @RequestParam(value = "onStatus", defaultValue = "0") byte onstatus) throws Exception {
		Shops oldShops = null;
		Integer operateShopId = null;
		
		Shops shop = shopsService.get(shop_id);
		if(shop.getPid()!=-1){
			oldShops = shopsService.get(shop.getPid());
			oldShops.setOnStatus(onstatus);
			oldShops.setUpdatedAt(new Date());
			shopsService.openOrClose(oldShops);
			operateShopId = oldShops.getId();
		}else{
			operateShopId = shop_id;
		}
		
		
		shop.setId(shop_id);
		shop.setOnStatus(onstatus);
		shop.setUpdatedAt(new Date());
		shopsService.openOrClose(shop);

		if(onstatus==0){
			jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(operateShopId), JobConstant.OperateType.SHOP_TURN_OFF);
		}else{
			jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(operateShopId), JobConstant.OperateType.SHOP_TURN_ON);
		}
		return "success";

	}

	/**
	 * @author ShaoYanbin 商铺推荐
	 * */
	@ResponseBody
	@RequestMapping(value = "/recomShop")
	public String recommend(HttpServletRequest request, @RequestParam("id") Integer shop_id, @RequestParam(value = "recommend", defaultValue = "0") byte recommend) throws Exception {
		Shops oldShops = null;
		Integer operateShopId = null;
		Shops shop = shopsService.get(shop_id);
		
		
		if(shop.getPid()!=-1){
			oldShops = shopsService.get(shop.getPid());
			oldShops.setRecommend((int)recommend);
			oldShops.setUpdatedAt(new Date());
			shopsService.recom(oldShops);
			operateShopId = oldShops.getId();
		}else{
			operateShopId = shop_id;
		}
		
		shop.setId(shop_id);
		shop.setRecommend((int) recommend);
		shop.setUpdatedAt(new Date());
		shopsService.recom(shop);
		
		
		if(recommend==0){
			jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(operateShopId), JobConstant.OperateType.SHOP_RECOMMEND_OFF);
		}else{
			jobService.insertJob(JobConstant.JobType.SHOP_TYPE, String.valueOf(operateShopId), JobConstant.OperateType.SHOP_RECOMMEND_ON);
		}
		
		return "success";

	}

	/**
	 * 商家详情查看
	 * 
	 * @param request
	 * @param shopsId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request, @RequestParam("shopsId") String shopsId, ModelMap modelMap) throws Exception {
		try {
			logger.info("welcome view... shopsId>>>" + shopsId);
			Shops shops = null;
			if (shopsId != null) {
				shops = shopsService.get(Integer.parseInt(shopsId));
				if (shops != null) {
					if (shops.getPid() == 0 && shops.getRecordType() == 0) {
						// 当前内容，去查待同步内容
						Shops sumAd = shopsService.selectByPid(shops.getId());
						modelMap.put("sumAdId", sumAd.getId());//
					}
					List<Photos> list = photosService.selectByOwnerIdAndType(shops.getId(), Constant.Photo.OwnerType1);
					if (list != null && list.size() > 0) {
						if (list.get(0) != null) {
							Photos p = list.get(0);
							if (StringUtils.isBlank(p.getFileUrl()) || "-1".equals(p.getFileUrl())) {
								// ../upload/shops/original/
								modelMap.put("pic", Constant.PicPath.rootPath + "/" + Constant.PicPath.shops + "/" + Constant.PicPath.original + "/" + p.getFile());//
							} else {
								modelMap.put("pic", p.getFileUrl());//
							}
						}
					}
					if(shops.getBrandId()!=null && shops.getBrandId().intValue()>0) {
						Brands b = brandsService.selectByPrimaryKey(shops.getBrandId());
						if(b != null) {
							shops.setBrands(b);
						}
					}
					Param floor = paramService.getFloor(shops.getZoneId());
					modelMap.put("floor", floor);//
				} else {
					throw new Exception();
				}
			}
			modelMap.put("shops", shops);//
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		// System.out.println("\n\n\n\n=============================================================");
		return "shops/view";
	}
	
	/**
	 * 商家预览
	 * 
	 * @param request
	 * @param shopsId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/preview")
	public String preview(HttpServletRequest request, @RequestParam("paramsJson") String paramsJson, ModelMap modelMap) throws Exception {
		try {
			logger.info("welcome preview... paramsJson>>>" + paramsJson);
			if (paramsJson == null) {
				throw new Exception();
			}
			Map<String,Object> paramsMap = JsonUtil.getMapFromJson(paramsJson);
			String shopsId = (String) paramsMap.get("shopsId");
			Shops shops = null;
			if (shopsId != null) {
				shops = shopsService.get(Integer.parseInt(shopsId));
				if (shops == null) {
					shops = new Shops();
				}
			}
			String shopsName = (String) paramsMap.get("shopsName");
			String brandId = (String) paramsMap.get("shopsLogo");
			String shopsTags = (String) paramsMap.get("shopsTags");
			String openTime = (String) paramsMap.get("openTime");
			String telephone = (String) paramsMap.get("telephone");
			String recommend = (String) paramsMap.get("recommend");
			String description = (String) paramsMap.get("description");
			shopsName = shopsName!=null ? URLDecoder.decode(shopsName, "UTF-8") : "";
			shopsTags = shopsTags!=null ? URLDecoder.decode(shopsTags, "UTF-8") : "";
			openTime = openTime!=null ? URLDecoder.decode(openTime, "UTF-8") : "";
			telephone = telephone!=null ? URLDecoder.decode(telephone, "UTF-8") : "";
			description = description!=null ? URLDecoder.decode(description, "UTF-8") : "";
			String shopsPic_0 = (String) paramsMap.get("shopsPic_0");
			String shopsPic_1 = (String) paramsMap.get("shopsPic_1");
			String shopsPic_2 = (String) paramsMap.get("shopsPic_2");
			String shopsPic_3 = (String) paramsMap.get("shopsPic_3");
			shops.setName(shopsName);
			shops.setBrandId(Integer.parseInt(brandId));
			Brands brands = brandsService.selectByPrimaryKey(shops.getBrandId());
			shops.setBrands(brands);
			shops.setTags(shopsTags);
			shops.setOpenTime(openTime);
			shops.setTelephone(telephone);
			shops.setRecommend(Integer.parseInt(recommend));
			shops.setDescription(description);
				
			modelMap.put("shops", shops);//
			modelMap.put("shopsPic_0", shopsPic_0);//
			modelMap.put("shopsPic_1", shopsPic_1);//
			modelMap.put("shopsPic_2", shopsPic_2);//
			modelMap.put("shopsPic_3", shopsPic_3);//
			if(shops.getZoneId() != null) {
				Param floor = paramService.getFloor(shops.getZoneId());
				modelMap.put("floor", floor);//
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return "shops/preview";
	}
	
	/**
	 * 验证是否有编辑过未保存的
	 * 2014.5.4.lim
	 * @param paramsJson
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/verifyChanged")
	public void verifyChanged(@RequestParam("paramsJson") String paramsJson, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramsMap;
		try {
			logger.info("welcome verifyChanged... paramsJson>>>" + paramsJson);
			if (paramsJson == null) {
				resultMap.put("msg", "参数为NULL，请关闭再重试！");
				resultMap.put("status", 0);
				return;
			}
			paramsMap = JsonUtil.getMapFromJson(paramsJson);

			String shopsId = (String) paramsMap.get("shopsId");
			if (shopsId == null || StringUtils.isBlank(shopsId.trim())) {
				throw new Exception("系统出错，请稍候重试！");
			} else {
				shopsId = shopsId.trim();
			}
			Shops shops = shopsService.get(Integer.parseInt(shopsId));
			if(shops == null) {
				throw new Exception("系统出错，请稍候重试！");
			}
			String shopsName = (String) paramsMap.get("shopsName");
			if (shopsName != null && StringUtils.isNotBlank(shopsName.trim())) {
				shopsName = URLDecoder.decode(shopsName, "UTF-8");
				if(!shopsName.equals(shops.getName())) {
					throw new Exception("商户名称已修改");
				}
			}
			String shopsLogo = (String) paramsMap.get("shopsLogo");
			if (shopsLogo != null && StringUtils.isNotBlank(shopsLogo.trim())) {
				shopsLogo = URLDecoder.decode(shopsLogo, "UTF-8");
				if(Integer.parseInt(shopsLogo) != shops.getBrandId().intValue()) {
					throw new Exception("品牌已修改");
				}
			}
			String shopsTags = (String) paramsMap.get("shopsTags");
			if (shopsTags != null && StringUtils.isNotBlank(shopsTags.trim())) {
				shopsTags = URLDecoder.decode(shopsTags, "UTF-8");
				logger.info(shopsTags.trim() + "<>" + shops.getTags().trim());
				if(!shopsTags.trim().equals(shops.getTags().trim())) {
					throw new Exception("商户标签已修改");
				}
			}
			String openTime = (String) paramsMap.get("openTime");
			if (openTime != null && StringUtils.isNotBlank(openTime.trim())) {
				openTime = URLDecoder.decode(openTime, "UTF-8");
				if(!openTime.equals(shops.getOpenTime())) {
					throw new Exception("营业时间已修改");
				}
			}
			String telephone = (String) paramsMap.get("telephone");
			if (telephone != null && StringUtils.isNotBlank(telephone.trim())) {
				telephone = URLDecoder.decode(telephone, "UTF-8");
				if(!telephone.equals(shops.getTelephone())) {
					throw new Exception("商家电话已修改");
				}
			}
			String description = (String) paramsMap.get("description");
			if (description != null && StringUtils.isNotBlank(description.trim())) {
				description = URLDecoder.decode(description, "UTF-8");
				logger.info("\n"+description.trim() + "\n" + shops.getDescription().trim());
				if(!description.equals(shops.getDescription())) {
					throw new Exception("商家简介已修改");
				}
			}
			String recommend = (String) paramsMap.get("recommend");
			if (recommend != null && StringUtils.isNotBlank(recommend)) {
				int recommend_ = Integer.parseInt(recommend);
				if(recommend_ != shops.getRecommend().intValue()) {
					throw new Exception("特别推荐已修改");
				}
			}
			String[] pics = { "shopsPic_1", "shopsPic_2", "shopsPic_3", "shopsPic_4" };
			String[] ids = { "shopsId_1", "shopsId_2", "shopsId_3", "shopsId_4" };
			String[] isUpdates = { "isUpdate_1", "isUpdate_2", "isUpdate_3", "isUpdate_4" };

			String shopsPic = null;
			Photos photo = null;
			int index = 0;
			String photoId = null;
			String isUpdated = null;
			for (String pic : pics) {
				shopsPic = (String) paramsMap.get(pic);
				isUpdated = (String) paramsMap.get(isUpdates[index]);
				photoId = (String) paramsMap.get(ids[index]);
				if (shopsPic != null && StringUtils.isNotBlank(shopsPic.trim())) {
					shopsPic = shopsPic.trim();
					if (isUpdated != null && "updated".equals(isUpdated)) {
						if (photoId != null && StringUtils.isNotBlank(photoId.trim())) {
							photo = photosService.get(Integer.parseInt(photoId));
						}else {
							throw new Exception("商家图片已修改");
						}
						if (photo == null) {
							throw new Exception("商家图片已修改");
						}
					}
				}else if("delete".equals(isUpdated)){
					//逻辑删除照片,把图片名字清空掉
					if (photoId != null && StringUtils.isNotBlank(photoId.trim())) {
						photo = photosService.get(Integer.parseInt(photoId));
						if(photo!=null && photo.getDeleteStatus().intValue() != Constant.Common.YES) {
							throw new Exception("商家图片已修改");
						}
					}
				}
			}
			resultMap.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("msg", e.getMessage());
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}
}
