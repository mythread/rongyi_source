package com.rongyi.nasdaq.common.pagination;

import java.util.List;

/**
 * 类PagesPagination.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月21日 下午5:07:23
 */
public class PagesPagination extends Pagination {

    private List<PageInfo> pages;
    private PageInfo       firstPage;
    private PageInfo       lastPage;
    private PageInfo       prevPage;
    private PageInfo       nextPage;

    public List<PageInfo> getPages() {
        return pages;
    }

    public void setPages(List<PageInfo> pages) {
        this.pages = pages;
    }

    public PageInfo getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(PageInfo firstPage) {
        this.firstPage = firstPage;
    }

    public PageInfo getLastPage() {
        return lastPage;
    }

    public void setLastPage(PageInfo lastPage) {
        this.lastPage = lastPage;
    }

    public PageInfo getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(PageInfo prevPage) {
        this.prevPage = prevPage;
    }

    public PageInfo getNextPage() {
        return nextPage;
    }

    public void setNextPage(PageInfo nextPage) {
        this.nextPage = nextPage;
    }

}
