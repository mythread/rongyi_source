package com.mallcms.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 配置商场的ip
 * 
 * @author jiejie 2014年4月16日 上午10:01:19
 */
public class MallIpConfig implements Serializable {

    private static final long serialVersionUID = -6044657116624813226L;

    private Integer           id;
    private Date              gmtCreate;
    private Date              gmtModified;
    private String            mallId;                                  // 商场id
    private String            ip;                                      // 商场服务器ip
    private Integer           port;                                    // socket的端口号
    private String            domain;                                  // 域名

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

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
