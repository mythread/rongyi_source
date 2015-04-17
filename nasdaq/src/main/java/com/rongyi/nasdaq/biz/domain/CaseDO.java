package com.rongyi.nasdaq.biz.domain;

import java.util.Date;

/**
 * 官网案例
 * 
 * @author jiejie 2014年5月19日 下午6:38:46
 */
public class CaseDO {

    private Integer id;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer status;     // 状态：0：可用，1：不可用
    private String  subject;    // 标题
    private String  mallName;
    private String  pic;        // 缩略图
    private String  content;    // 正文

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
