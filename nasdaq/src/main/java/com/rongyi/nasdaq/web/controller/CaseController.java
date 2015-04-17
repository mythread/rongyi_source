package com.rongyi.nasdaq.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rongyi.nasdaq.biz.domain.CaseDO;
import com.rongyi.nasdaq.biz.service.WebSiteService;
import com.rongyi.nasdaq.common.pagination.PagesPagination;
import com.rongyi.nasdaq.web.html.HtmlMeta;

/**
 * 案列
 * 
 * @author jiejie 2014年5月18日 下午10:27:30
 */
@Controller
@RequestMapping(value = "/case")
public class CaseController extends BaseController {

    private static Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    @Autowired
    private WebSiteService               service;

    @RequestMapping(value = { "list{page}.htm", "list_{page}.htm" })
    public ModelAndView list(@PathVariable("page")
    Integer page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("case/list");
        // 设置顶部导航，选择第6个标签
        modelAndView.addObject(NAVSELECTED, 3);
        if (page == null || page <= 0) {
            page = 0;
        } else {
            page = page - 1;
        }
        Integer totalNum = service.countCase();
        PagesPagination pagination = null;
        List<CaseDO> caseList = new ArrayList<CaseDO>();
        if (totalNum > 0) {
            pagination = getPagination(page, 12, totalNum);
            caseList = service.listPageCase(pagination.getStartRecordIndex(), pagination.getPageSize());
            initPages(pagination);
        }
        modelAndView.addObject("caseList", caseList);
        modelAndView.addObject("pagination", pagination);
        return modelAndView;
    }

    @RequestMapping(value = "{id}.htm")
    public ModelAndView detail(@PathVariable("id")
    Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("case/detail");
        // 设置顶部导航，选择第6个标签
        modelAndView.addObject(NAVSELECTED, 3);

        CaseDO caseDO = service.getCaseById(id);
        modelAndView.addObject("caseDO", caseDO);
        CaseDO preCase = null, nextCase = null;
        preCase = service.getPrevCase(id);
        if (preCase == null) {
            preCase = caseDO;
        }
        nextCase = service.getNextCase(id);
        if (nextCase == null) {
            nextCase = caseDO;
        }
        HtmlMeta htmlMeta = getHtmlMeta(caseDO.getSubject(), caseDO.getSubject(), caseDO.getMallName());
        Integer readCount = increment(id);
        modelAndView.addObject("preCase", preCase);
        modelAndView.addObject("nextCase", nextCase);
        modelAndView.addObject("htmlMeta", htmlMeta);
        modelAndView.addObject("readCount", readCount);
        return modelAndView;
    }

    @Override
    protected String getPageUrl(Object... objects) {
        Integer page = (Integer) objects[1];
        return "/case/list_" + page + ".htm";
    }

    private synchronized int increment(Integer caseId) {
        Integer count = map.get(caseId);
        if (count == null) {
            count = new Random().nextInt(100) + 200;
        } else {
            count++;
        }
        map.put(caseId, count);
        return count;
    }

}
