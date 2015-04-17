package com.rongyi.nasdaq.biz.domain;

import java.util.Date;

/**
 * 文章
 * 
 * @author jiejie 2014年5月19日 下午7:35:49
 */
public class ArticleDO {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer status;       // 状态：0：可用，1：不可用
    private Integer articleStatus; // 0：草稿，1：发布状态
    private String  type;         // 媒体...
    private String  subject;      // 标题
    private String  source;       // 来源
    private String  summary;      // 摘要
    private String  content;      // 正文
    private Integer readCount;    // 浏览次数
    private String  author;       // 作者
    private Date    publishDate;  // 发表时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public static enum ArticleTypeEnum {

        /**
         * 媒体报道
         */
        MEDIA;
    }

}
