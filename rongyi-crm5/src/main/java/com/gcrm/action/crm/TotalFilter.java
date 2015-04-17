package com.gcrm.action.crm;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionSupport;

public class TotalFilter extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String            url;

    public String getUrl() {
        return url;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public String execute() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        String result = (String) request.getAttribute("jsp");
        User user = UserUtil.getLoginUser();
       if(isAdminRole(user)){
           request.setAttribute("admin", true);
       }else{
           request.setAttribute("admin", false);
       }
        url = result;
        if (url != null) {
            if (request.getQueryString() != null) {
                url = url + "?" + request.getQueryString();
            }
            String entityLabel = null;
            int beginIndex = url.indexOf("list");
            if (beginIndex != -1) {
                beginIndex += 4;
                int endIndex = url.indexOf(".jsp");
                String entityName = url.substring(beginIndex, endIndex);
                entityName = CommonUtil.lowerCaseString(entityName);
                entityLabel = getText("entity." + entityName + ".label") + " " + getText("title.action.list");
                HttpSession session = request.getSession();
                ArrayList navigationList = (ArrayList) session.getAttribute(Constant.NAVIGATION_HISTORY);
                if (navigationList == null) {
                    navigationList = new ArrayList();
                }
                String navigatoin = "<a href='" + url + "'>" + entityLabel + "</a>";
                if (navigationList.contains(navigatoin)) {
                    navigationList.remove(navigatoin);
                }
                navigationList.add(navigatoin);
                if (navigationList.size() > Constant.NAVIGATION_HISTORY_COUNT) {
                    navigationList.remove(0);
                }
                session.setAttribute(Constant.NAVIGATION_HISTORY, navigationList);
            }
        }
        return "url";
    }
    
    /**
     * 判断是否是admin角色
     */
    private boolean isAdminRole(User user){
        if(user == null){
            return false;
        }
        Set<Role> roleSet = user.getRoles();
        for(Role r : roleSet){
            if(r.getName().toLowerCase().contains("admin")){
                return true;
            }
        }
        return false;
    }
}
