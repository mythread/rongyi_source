package com.fanxian.web.fanxian.valve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanxian.commons.cookie.manager.CookieManager;
import com.fanxian.commons.cookie.manager.CookieManagerLocator;
import com.fanxian.web.fanxian.webuser.FanxianWebUser;
import com.fanxian.web.fanxian.webuser.FanxianWebUserBuild;
import com.yue.commons.seine.web.pipeline.AbstractPipelineValves;
import com.yue.commons.seine.web.pipeline.PipelineMap;
import com.yue.commons.seine.web.pipeline.PipelineResult;

public class WebUserBuilderValve extends AbstractPipelineValves {

    @Override
    public PipelineResult invoke(HttpServletRequest request, HttpServletResponse response, PipelineMap map)
                                                                                                           throws Exception {
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        FanxianWebUser webUser = FanxianWebUserBuild.createWebUser(cookieManager);
        FanxianWebUser.setCurrentUser(webUser);
        return super.invoke(request, response, map);
    }

}
