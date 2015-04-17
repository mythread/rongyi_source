package com.fanxian.web.fanxian.controller;

import javax.servlet.http.HttpServletRequest;

import com.fanxian.biz.user.service.UserService;
import com.fanxian.biz.user.service.UserServiceLocator;
import com.fanxian.biz.userandpro.service.UserAndProService;
import com.fanxian.biz.userandpro.service.UserAndProServiceLocator;
import com.fanxian.taobao.TaobaoService;
import com.fanxian.taobao.TaobaoServiceLocator;
import com.yue.commons.seine.web.servlet.mvc.AbstractController;

public class BaseController extends AbstractController {

    protected UserService         userService       = UserServiceLocator.getUserService();
    protected UserAndProService   userAndProService = UserAndProServiceLocator.getUserAndProService();
    protected TaobaoService       taobaoService     = TaobaoServiceLocator.getTaobaoService();

    protected static final String IP_QQWRY_DAT      = "/ip_qqwry.dat";

    public BaseController() {
        setNameSpace("fanxian");
    }

    /**
     * 获得ip
     * 
     * @param request
     * @return
     */
    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        // X-Real-IP
        // Proxy-Client-IP
        // WL-Proxy-Client-IP
        return request.getRemoteAddr();
    }
}
