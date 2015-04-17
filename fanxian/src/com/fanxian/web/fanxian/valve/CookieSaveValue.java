package com.fanxian.web.fanxian.valve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanxian.commons.cookie.manager.CookieManagerLocator;
import com.yue.commons.seine.web.pipeline.AbstractPipelineValves;
import com.yue.commons.seine.web.pipeline.PipelineMap;
import com.yue.commons.seine.web.pipeline.PipelineResult;

public class CookieSaveValue extends AbstractPipelineValves {

    @Override
    public PipelineResult invoke(HttpServletRequest request, HttpServletResponse response, PipelineMap map)
                                                                                                           throws Exception {
        com.fanxian.commons.cookie.manager.CookieManager cookieManager = CookieManagerLocator.get(request, response);
        cookieManager.save();
        return super.invoke(request, response, map);
    }

}
