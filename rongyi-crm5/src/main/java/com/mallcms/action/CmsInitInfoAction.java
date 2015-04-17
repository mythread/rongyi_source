package com.mallcms.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.mallcms.service.ICmsInitInfoService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author rongyi11 商铺信息初始化 ation
 */
public class CmsInitInfoAction extends ActionSupport implements ServletResponseAware {

    private static final long   serialVersionUID = 1L;
    private ICmsInitInfoService cmsInitInfoServiceImpl;

    private String              dbIp;                  // 服务器ip地址
    private String              dbUser;                // 数据库用户名
    private String              dbPassword;            // 数据库密码
    private String              mallId;                // 商城Id

    /**
     * 初始化商城信息
     * 
     * @return json
     * @throws IOException
     */
    public String initMallInfo() throws IOException {
        System.out.println("initmallaction");
        String sucess = cmsInitInfoServiceImpl.initMallInfo(mallId, dbIp, dbUser, dbPassword);
        ServletActionContext.getResponse().getWriter().print(sucess);
        return null;
    }

    /**
     * 初始化广告位信息
     * 
     * @return json
     */
    public String initAdzonesInfo() {
        System.out.println("initAdzonesInfo");
        String sucess = cmsInitInfoServiceImpl.initAdzonesInfo(mallId, dbIp, dbUser, dbPassword);
        try {
            ServletActionContext.getResponse().getWriter().print(sucess);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化广告详情信息
     * 
     * @return json
     */
    public String initAdvertisementsInfo() {
        System.out.println("initAdvertisementsInfo");
        String sucess = cmsInitInfoServiceImpl.initAdvertisementsInfo(mallId, dbIp, dbUser, dbPassword);
        try {
            ServletActionContext.getResponse().getWriter().print(sucess);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化商铺信息
     * 
     * @return json
     */
    public String initShopsInfo() {
        String sucess = cmsInitInfoServiceImpl.initShopsInfo(mallId, dbIp, dbUser, dbPassword);
        try {
            ServletActionContext.getResponse().getWriter().print(sucess);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化分类信息
     * 
     * @return json
     */
    public String initCategoriesInfo() {
        System.out.println("initCategoriesInfo");
        String sucess = cmsInitInfoServiceImpl.initCategoriesInfo(mallId, dbIp, dbUser, dbPassword);
        try {
            ServletActionContext.getResponse().getWriter().print(sucess);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化品牌信息
     * 
     * @return json
     */
    public String initBrandsInfo() {
        System.out.println("initBrandsInfo");
        String sucess = cmsInitInfoServiceImpl.initBrandsInfo(mallId, dbIp, dbUser, dbPassword);
        try {
            ServletActionContext.getResponse().getWriter().print(sucess);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化图片信息
     * 
     * @return json
     */
    public String initPhotosInfo() {
        System.out.println("initPhotosInfo");
        String sucess = cmsInitInfoServiceImpl.initPhotosInfo(mallId, dbIp, dbUser, dbPassword);
        try {
            ServletActionContext.getResponse().getWriter().print(sucess);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    public void setServletResponse(HttpServletResponse arg0) {

    }

    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public ICmsInitInfoService getCmsInitInfoServiceImpl() {
        return cmsInitInfoServiceImpl;
    }

    public void setCmsInitInfoServiceImpl(ICmsInitInfoService cmsInitInfoServiceImpl) {
        this.cmsInitInfoServiceImpl = cmsInitInfoServiceImpl;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

}
