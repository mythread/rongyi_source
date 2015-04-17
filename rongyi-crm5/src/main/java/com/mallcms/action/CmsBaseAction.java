package com.mallcms.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author jiejie 2014年4月16日 上午10:20:20
 */
public class CmsBaseAction extends ActionSupport {

    private static final long serialVersionUID = 7311460621811872804L;

    /**
     * 返回json数据
     */
    protected void response2Json(Object obj, boolean success, String msg) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            map.put("data", "");
        } else {
            map.put("data", obj);
        }
        map.put("msg", msg);
        if (success) {
            map.put("result", "0");
        } else {
            map.put("result", "1");
        }
        JSONObject json = JSONObject.fromObject(map);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json.toString());
    }

}
