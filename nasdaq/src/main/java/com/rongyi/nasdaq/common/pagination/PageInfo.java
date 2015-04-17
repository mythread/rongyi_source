package com.rongyi.nasdaq.common.pagination;

/**
 * 分页---页面显示的
 * 
 * @author jiejie 2014年5月21日 下午4:25:24
 */
public class PageInfo {

    private String  text;
    private String  url;
    private int     page;
    private boolean current; // 是否是当前页

    public PageInfo(String text, int page, boolean current, String url) {
        super();
        this.text = text;
        this.url = url;
        this.page = page;
        this.current = current;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

}
