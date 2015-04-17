package com.rongyi.nasdaq.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.rongyi.nasdaq.common.pagination.PageInfo;
import com.rongyi.nasdaq.common.pagination.PagesPagination;
import com.rongyi.nasdaq.common.pagination.SearchCons;
import com.rongyi.nasdaq.web.html.HtmlMeta;

/**
 * 类BaseController.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月22日 下午9:24:11
 */
public class BaseController {

    protected static final String NAVSELECTED        = "navSelected";
    protected static final String LEFTNAVSELECTED    = "leftNavSelected";
    protected static final String PRODUCTNAVSELECTED = "productNavSelected";
    protected static Integer      DEFAULT_LIMIT_SIZE = 12;

    protected PagesPagination getPagination(Integer page, int pageSize, int totalNum) {
        PagesPagination pagination = new PagesPagination();
        pagination.setPageSize(pageSize);
        pagination.setNowPageIndex(page);
        pagination.init(totalNum);
        return pagination;
    }

    /**
     * 生成分页信息
     * 
     * @param pagination
     */
    protected void initPages(PagesPagination pagination, Object... objects) {

        int index = pagination.getFirstPageIndex();
        PageInfo firstpage = new PageInfo(SearchCons.FIRSTPAGE, index + 1, pagination.isFirstPage(),
                                          getPageUrl(objects, index + 1));
        pagination.setFirstPage(firstpage);
        // 上一页
        index = pagination.getPrevPageIndex();
        PageInfo prevpage = new PageInfo(SearchCons.PREPAGE, index + 1, false, getPageUrl(objects, index + 1));
        pagination.setPrevPage(prevpage);
        // 下一页
        index = pagination.getNextPageIndex();
        PageInfo nextpage = new PageInfo(SearchCons.NEXTPAGE, index + 1, false, getPageUrl(objects, index + 1));
        pagination.setNextPage(nextpage);
        // 尾页
        index = pagination.getLastPageIndex();
        PageInfo lastpage = new PageInfo(SearchCons.LASTPAGE, index + 1, pagination.isLastPage(), getPageUrl(objects,
                                                                                                             index + 1));
        pagination.setLastPage(lastpage);
        //
        int nowPageIndex = pagination.getNowPageIndex();
        List<PageInfo> pages = new ArrayList<PageInfo>();
        List<Integer> skipPageIndexs = pagination.getSkipPageIndex();
        for (Integer integer : skipPageIndexs) {
            pages.add(new PageInfo(StringUtils.EMPTY + (integer + 1), integer + 1, nowPageIndex == integer,
                                   getPageUrl(objects, integer + 1)));
        }
        pagination.setPages(pages);
    }

    /**
     * 不同实例的分页页面需要重写此方法
     * 
     * @return
     */
    protected String getPageUrl(Object... objects) {
        return "";
    };

    public static HtmlMeta getHtmlMeta(String title, String description, String keywords) {
        HtmlMeta meta = new HtmlMeta(title, description, keywords);
        return meta;
    }
}
