package com.rongyi.nasdaq.web.html;

/**
 * 类HtmlMet.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月28日 下午6:16:13
 */
public class HtmlMeta {

    private String title;
    private String description;
    private String keywords;

    public HtmlMeta(String title, String description, String keywords) {
        this.title = title;
        this.description = description;
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
