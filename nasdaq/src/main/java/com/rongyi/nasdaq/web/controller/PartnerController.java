package com.rongyi.nasdaq.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rongyi.nasdaq.biz.domain.PartnerDO;
import com.rongyi.nasdaq.biz.service.WebSiteService;
import com.rongyi.nasdaq.common.pagination.PagesPagination;

@Controller
@RequestMapping(value = "/partner")
public class PartnerController extends BaseController {

    @Autowired
    public WebSiteService service;

    @RequestMapping("list_{page}.htm")
    public ModelAndView partner(@PathVariable("page")
    Integer page) {
        return getPartnerModelAndView(page);
    }

    @RequestMapping("list.htm")
    public ModelAndView partner() {
        return getPartnerModelAndView(null);
    }

    /**
     * @param page
     * @return
     */
    private ModelAndView getPartnerModelAndView(Integer page) {
        ModelAndView modelAndView = new ModelAndView("partner");
        modelAndView.addObject(NAVSELECTED, 4);
        if (page == null || page <= 0) {
            page = 0;
        } else {
            page = page - 1;
        }
        Integer totalNum = service.countPartner();
        PagesPagination pagination = null;
        List<PartnerDO> partnerList = new ArrayList<PartnerDO>();
        if (totalNum > 0) {
            pagination = getPagination(page, 8, totalNum);
            partnerList = service.listPagePartner(pagination.getStartRecordIndex(), pagination.getPageSize());
            initPages(pagination);
        }
        modelAndView.addObject("partnerList", partnerList);
        modelAndView.addObject("pagination", pagination);
        return modelAndView;
    }

    @Override
    protected String getPageUrl(Object... objects) {
        Integer page = (Integer) objects[1];
        return "/partner/list_" + page + ".htm";
    }

}
