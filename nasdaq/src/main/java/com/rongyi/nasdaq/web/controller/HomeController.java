package com.rongyi.nasdaq.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rongyi.nasdaq.biz.domain.ArticleDO;
import com.rongyi.nasdaq.biz.domain.ArticleDO.ArticleTypeEnum;
import com.rongyi.nasdaq.biz.domain.CaseDO;
import com.rongyi.nasdaq.biz.domain.HomePictureDO;
import com.rongyi.nasdaq.biz.domain.PartnerDO;
import com.rongyi.nasdaq.biz.service.WebSiteService;

/**
 * @author jiejie 2014年5月18日 下午10:27:30
 */
@Controller
public class HomeController extends BaseController {

    @Autowired
    private WebSiteService service;

    @RequestMapping(value = "/home.htm")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        // 设置顶部导航，选择第一个标签
        modelAndView.addObject(NAVSELECTED, 1);

        // 首页轮播图
        List<HomePictureDO> homePicList = service.listHomePic();
        modelAndView.addObject("homePicList", homePicList);

        // 案列分享
        List<CaseDO> caseList = service.listCase(DEFAULT_LIMIT_SIZE);
        modelAndView.addObject("caseList", caseList);

        // 媒体报道
        List<ArticleDO> articleList = service.listArticle(8, ArticleTypeEnum.MEDIA.name());
        modelAndView.addObject("articleList", articleList);

        // 合作伙伴
        List<PartnerDO> partnerList = service.listPartner(27);
        modelAndView.addObject("partnerList", partnerList);
        return modelAndView;
    }
}
