package com.rongyi.nasdaq.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rongyi.nasdaq.biz.domain.ArticleDO;
import com.rongyi.nasdaq.biz.domain.ArticleDO.ArticleTypeEnum;
import com.rongyi.nasdaq.biz.service.WebSiteService;
import com.rongyi.nasdaq.common.pagination.PagesPagination;
import com.rongyi.nasdaq.common.util.Argument;
import com.rongyi.nasdaq.web.html.HtmlMeta;

/**
 * 关于我们
 * 
 * @author jiejie 2014年5月18日 下午10:27:30
 */
@Controller
@RequestMapping(value = "/about")
public class AboutController extends BaseController {

    @Autowired
    private WebSiteService service;

    @RequestMapping(value = "/introduce.htm")
    public ModelAndView introduce() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about/introduce");

        // 设置顶部导航，选择第7个标签，左侧导航，选择低2个标签
        modelAndView.addObject(NAVSELECTED, 7);
        modelAndView.addObject(LEFTNAVSELECTED, 1);

        return modelAndView;
    }

    /**
     * 媒体报道
     * 
     * @return
     */
    @RequestMapping(value = "/newslist_{page}.htm")
    public ModelAndView newslist(@PathVariable("page")
    Integer page) {
        return getNewListModelAndView(page);
    }

    @RequestMapping("/newslist.htm")
    public ModelAndView newslist() {
        return getNewListModelAndView(null);
    }

    /**
     * 返回媒体报道的列表页
     * 
     * @param page
     * @return
     */
    private ModelAndView getNewListModelAndView(Integer page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about/newslist");
        // 设置顶部导航，选择第7个标签，左侧导航，选择低2个标签
        modelAndView.addObject(NAVSELECTED, 7);
        modelAndView.addObject(LEFTNAVSELECTED, 2);

        if (Argument.isNotPositive(page)) {
            page = 0;
        } else {
            page = page - 1;
        }
        Integer totalNum = service.countArticle(ArticleTypeEnum.MEDIA.name());
        PagesPagination pagination = null;
        List<ArticleDO> articleList = new ArrayList<ArticleDO>();
        if (totalNum > 0) {
            pagination = getPagination(page, 8, totalNum);
            articleList = service.listPageArticle(pagination.getStartRecordIndex(), pagination.getPageSize(),
                                                  ArticleTypeEnum.MEDIA.name());
            initPages(pagination);
        }
        modelAndView.addObject("articleList", articleList);
        modelAndView.addObject("pagination", pagination);
        return modelAndView;
    }

    @RequestMapping(value = "/newsdetail/{id}.htm")
    public ModelAndView newsdetail(@PathVariable("id")
    Integer id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about/newsdetail");

        // 设置顶部导航，选择第7个标签，左侧导航，选择低2个标签
        modelAndView.addObject(NAVSELECTED, 7);
        modelAndView.addObject(LEFTNAVSELECTED, 2);

        ArticleDO articleDO = null;
        articleDO = service.getArticleById(id);
        if (articleDO != null) {
            service.updateArticleRead(id);
        }
        modelAndView.addObject("articleDO", articleDO);
        ArticleDO preArticle = null, nextArticle = null;
        preArticle = service.getPrevArticle(id);
        if (preArticle == null) {
            preArticle = articleDO;
        }
        nextArticle = service.getNextArticle(id);
        if (nextArticle == null) {
            nextArticle = articleDO;
        }
        HtmlMeta htmlMeta = getHtmlMeta(articleDO.getSubject(), articleDO.getSummary(), articleDO.getSource());
        modelAndView.addObject("preArticle", preArticle);
        modelAndView.addObject("nextArticle", nextArticle);
        modelAndView.addObject("htmlMeta", htmlMeta);
        return modelAndView;
    }

    @RequestMapping(value = "/area.htm")
    public ModelAndView area() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about/area");

        // 设置顶部导航，选择第7个标签，左侧导航，选择低2个标签
        modelAndView.addObject(NAVSELECTED, 7);
        modelAndView.addObject(LEFTNAVSELECTED, 3);

        return modelAndView;
    }

    @RequestMapping(value = "/business.htm")
    public ModelAndView business() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about/business");

        // 设置顶部导航，选择第7个标签，左侧导航，选择低2个标签
        modelAndView.addObject(NAVSELECTED, 7);
        modelAndView.addObject(LEFTNAVSELECTED, 4);

        return modelAndView;
    }

    @RequestMapping(value = "/contact.htm")
    public ModelAndView contact() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about/contact");

        // 设置顶部导航，选择第7个标签，左侧导航，选择低2个标签
        modelAndView.addObject(NAVSELECTED, 7);
        modelAndView.addObject(LEFTNAVSELECTED, 5);

        return modelAndView;
    }

    @Override
    protected String getPageUrl(Object... objects) {
        Integer page = (Integer) objects[1];
        return "/about/newslist_" + page + ".htm";
    }

}
