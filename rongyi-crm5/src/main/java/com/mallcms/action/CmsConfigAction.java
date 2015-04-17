package com.mallcms.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gcrm.service.IBaseService;
import com.mallcms.domain.MallIpConfig;

/**
 * @author jiejie 2014年4月16日 上午10:19:45
 */
public class CmsConfigAction extends CmsBaseAction {

    private static final long          serialVersionUID = 6690411800113614863L;
    private IBaseService<MallIpConfig> mallConfigService;
    private String                     mallId;
    private String                     ip;
    private Integer                    port;
    private String                     domain;                                 // 域名
    private List<MallIpConfig>         mallConfigList;

    /**
     * 添加配置信息
     */
    public String addConfig() throws Exception {
        if (StringUtils.isEmpty(mallId) || StringUtils.isEmpty(ip) || port == null) {
            response2Json(null, false, "请将数据填写完整！");
            return null;
        }
        MallIpConfig config = new MallIpConfig();
        Date now = new Date();
        config.setGmtCreate(now);
        config.setGmtModified(now);
        config.setIp(ip);
        config.setMallId(mallId);
        config.setPort(port);
        config.setDomain(domain);
        config = mallConfigService.makePersistent(config);
        if (config.getId() != null) {
            response2Json(config.getId(), true, "操作成功");
        } else {
            response2Json(null, false, "");
        }
        return null;
    }

    /**
     * 查询列表
     * 
     * @return
     */
    public String listMallIp() {
        String hql = "from MallIpConfig order by id desc ";
        mallConfigList = mallConfigService.findByHQL(hql);
        return SUCCESS;
    }

    public IBaseService<MallIpConfig> getMallConfigService() {
        return mallConfigService;
    }

    public void setMallConfigService(IBaseService<MallIpConfig> mallConfigService) {
        this.mallConfigService = mallConfigService;
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

    public List<MallIpConfig> getMallConfigList() {
        return mallConfigList;
    }

    public void setMallConfigList(List<MallIpConfig> mallConfigList) {
        this.mallConfigList = mallConfigList;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
