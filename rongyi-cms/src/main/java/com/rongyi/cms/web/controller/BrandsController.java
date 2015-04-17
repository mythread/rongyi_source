package com.rongyi.cms.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import base.page.Pagination;
import base.util.character.JsonUtil;

import com.rongyi.cms.bean.Brands;
import com.rongyi.cms.bean.Categories;
import com.rongyi.cms.service.BrandsService;
import com.rongyi.cms.service.CateLinkBrandsService;
import com.rongyi.cms.service.CategoriesService;
import com.rongyi.cms.web.form.BrandsVO;
import com.rongyi.cms.web.form.CategoryVO;

@Controller
@RequestMapping("/brands")
public class BrandsController {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private BrandsService brandsService;
	@Autowired
	private CateLinkBrandsService cateLinkBrandsService;

	@RequestMapping(value = "/categorieslist")
	public void categorieslist(HttpServletRequest request,@RequestParam(value = "categoryId", defaultValue = "") String categoryId,HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Categories cate = new Categories();
		try {
			List<Categories> cateList = null;
			String type = "1";
			if (StringUtils.isBlank(categoryId)) {
				cateList = categoriesService.selectAllParentCategories();
			} else {
				Categories categories = new Categories();
				categories.setParentId(categoryId);
				cateList = categoriesService.selectSubCategoriesByParentId(categories);
				cate = categoriesService.selectByPrimaryKey(categoryId);
				type = "2";
			}
			resultMap.put("cateList", cateList);
			resultMap.put("type", type);
			resultMap.put("pcast", cate);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	@RequestMapping(value = "/brandslist")
	public void brandslist(
			HttpServletRequest request,
			@RequestParam(value = "categoryId", defaultValue = "") String categoryId,
			@RequestParam(value = "categoryType", defaultValue = "") String categoryType,
			@RequestParam(value = "paging", defaultValue = "") String paging,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String status = "1";
 		Pagination pagination = new Pagination(request);
		pagination.setPageSize(50);
		pagination.setCurrentPage(Integer.valueOf(paging));
		
		try {
			List<Brands> brandsList = null;
			List<BrandsVO> brandsVOList = null;
			Categories categories = new Categories();
			categories.setId(categoryId);
			categories.setPagination(pagination);
			if (categoryType.equals("1")) {
				brandsList = brandsService
						.listPageselectBrandsByParentCategoryId(categories);
			} else {
				brandsList = brandsService
						.selectBrandsBySubCategoryId(categories);
			}

			if (brandsList != null && brandsList.size() > 0) {
				brandsVOList = new ArrayList<BrandsVO>();
				for (Brands brands : brandsList) {
					BrandsVO brandsVO = new BrandsVO();
					BeanUtils.copyProperties(brands, brandsVO);
					brandsVOList.add(brandsVO);
				}
			}
			
			if(brandsList.size()<50){
				status ="0";
			}

			resultMap.put("brandsList", brandsVOList);
			resultMap.put("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	@RequestMapping(value = "/allcategorylist")
	public void allCategoryList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			list = categoriesService.selectCategories();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String json = JsonUtil.getJSONString(list);
			logger.info(json);
			responseJson(response, json);
		}
		return;
	}

	@RequestMapping(value = "/searchbrands")
	public void searchbrands(
			HttpServletRequest request,
			@RequestParam(value = "searchParam", defaultValue = "") String searchParam,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Brands> brandsList = null;
		List<BrandsVO> brandsVOList = null;
		try {
			if (!StringUtils.isBlank(searchParam.trim())) {
				Brands brands = new Brands();

				brands.setName(searchParam.trim());
				brands.setEname(searchParam.trim());
				brands.setEname(searchParam.trim());
				brandsList = brandsService.selectBrandsByCEName(brands);
				
				if (brandsList != null && brandsList.size() > 0) {
					brandsVOList = new ArrayList<BrandsVO>();
					for (Brands brand : brandsList) {
						BrandsVO brandsVO = new BrandsVO();
						BeanUtils.copyProperties(brand, brandsVO);
						brandsVOList.add(brandsVO);
					}
					resultMap.put("brandsList", brandsVOList);
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

	@RequestMapping(value = "/createnewbrands")
	public void createBrands(
			HttpServletRequest request,
			@RequestParam(value = "filename", defaultValue = "") String filename,
			@RequestParam(value = "logoname", defaultValue = "") String logoname,
			@RequestParam(value = "categoryidstr", defaultValue = "") String categoryidstr,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Brands brands = new Brands();
			brands.setName(logoname);
			brands.setCname(logoname);
			brands.setIconUrl("-1");
			brands.setSyhchStatus(1);
			brands.setIcon(filename);
			int brandsId = brandsService.insert(brands, categoryidstr);
			resultMap.put("newBrandsId", brandsId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
		}
	}

	/**
	 * 直接输出json
	 */
	public void responseJson(HttpServletResponse response, String json) {
		doPrint(response, json, "application/json;charset=UTF-8");
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
	public void doPrint(HttpServletResponse response, String text,
			String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			System.out.println("向客户端输出时发生异" + e.getMessage());
		}
	}

}
