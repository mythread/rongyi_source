package com.rongyi.nasdaq.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rongyi.nasdaq.biz.domain.JobDO;
import com.rongyi.nasdaq.biz.domain.JobPlaceDO;
import com.rongyi.nasdaq.biz.domain.JobTypeDO;
import com.rongyi.nasdaq.biz.service.WebSiteService;
import com.rongyi.nasdaq.common.pagination.PagesPagination;
import com.rongyi.nasdaq.common.util.Argument;

/**
 * 人才招聘
 * 
 * @author jiejie 2014年5月18日 下午10:27:30
 */
@Controller
@RequestMapping(value = "/job")
public class RecruitController extends BaseController {

    @Autowired
    private WebSiteService service;

    /**
     * 招聘列表页
     * 
     * @param typeId
     * @param locationId
     * @param page
     * @return
     */
    @RequestMapping(value = "list/{type}/{location}/{page}.htm")
    public ModelAndView list(@PathVariable("type")
    Integer typeId, @PathVariable("location")
    Integer locationId, @PathVariable("page")
    Integer page) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recruit/list");
        // 设置顶部导航，选择第6个标签
        modelAndView.addObject(NAVSELECTED, 6);
        getModelAndView(modelAndView, typeId, locationId, page);
        return modelAndView;
    }

    @RequestMapping("list.htm")
    public ModelAndView list() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recruit/list");
        // 设置顶部导航，选择第6个标签
        modelAndView.addObject(NAVSELECTED, 6);
        getModelAndView(modelAndView, null, null, null);
        return modelAndView;
    }

    /**
     * 职位详情页
     * 
     * @param typeId
     * @param locationId
     * @param id
     * @return
     */
    @RequestMapping(value = "detail/{type}/{location}/{id}.htm")
    public ModelAndView detail(@PathVariable("type")
    Integer typeId, @PathVariable("location")
    Integer locationId, @PathVariable("id")
    Integer id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recruit/detail");
        // 设置顶部导航，选择第6个标签
        modelAndView.addObject(NAVSELECTED, 6);
        if (typeId == null || typeId < 0) {
            typeId = 0;
        }
        if (locationId == null || locationId < 0) {
            locationId = 0;
        }
        JobTypeAndPlace2Model(modelAndView);
        modelAndView.addObject("selectTypeId", typeId);
        modelAndView.addObject("selectPlaceId", locationId);
        JobDO jobDO = service.getJobById(id);
        modelAndView.addObject("jobDO", jobDO);
        getHtmlMeta(jobDO.getName(), jobDO.getName(), jobDO.getName());
        return modelAndView;
    }

    /**
     * job list页面
     */
    private ModelAndView getModelAndView(ModelAndView model, Integer typeId, Integer locationId, Integer page) {
        if (Argument.isNotPositive(typeId)) {
            typeId = 0;
        }
        if (Argument.isNotPositive(locationId)) {
            locationId = 0;
        }
        if (Argument.isNotPositive(page)) {
            page = 0;
        } else {
            page = page - 1;
        }

        JobTypeAndPlace2Model(model);

        // 职位
        Integer totalNum = service.countJob(locationId, typeId);
        PagesPagination pagination = null;
        List<JobDO> jobList = new ArrayList<JobDO>();
        if (totalNum > 0) {
            pagination = getPagination(page, 10, totalNum);
            jobList = service.listPageJob(pagination.getStartRecordIndex(), pagination.getPageSize(), locationId,
                                          typeId);
            initPages(pagination, typeId, locationId);

        }
        model.addObject("jobList", jobList);
        model.addObject("pagination", pagination);
        model.addObject("selectTypeId", typeId);
        model.addObject("selectPlaceId", locationId);

        return model;
    }

    /**
     * @param model
     */
    private void JobTypeAndPlace2Model(ModelAndView model) {
        // 职位类别
        List<JobTypeDO> jobTypeList = service.listJobType(5);
        model.addObject("jobTypeList", jobTypeList);

        // 地区
        List<JobPlaceDO> jobPlaceList = service.listJobPlace(5);
        model.addObject("jobPlaceList", jobPlaceList);
    }

    @Override
    protected String getPageUrl(Object... objects) {
        Object[] ids = (Object[]) objects[0];
        Integer typeId = (Integer) ids[0];
        Integer locationId = (Integer) ids[1];
        Integer page = (Integer) objects[1];
        return "/job/list/" + typeId + "/" + locationId + "/" + page + ".htm";
    }
}
