package com.fanxian.web.fanxian.valve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.fanxian.web.common.util.InvokeTypeTools;
import com.fanxian.web.common.util.JsonResultUtils;
import com.yue.commons.seine.web.pipeline.AbstractPipelineValves;
import com.yue.commons.seine.web.pipeline.PipelineMap;
import com.yue.commons.seine.web.pipeline.PipelineResult;

public class ExceptionValve extends AbstractPipelineValves {

    public void init(ApplicationContext context) {
        super.init(context);
    }

    public PipelineResult invoke(HttpServletRequest request, HttpServletResponse response, PipelineMap map)
                                                                                                           throws Exception {
        boolean isAjax = InvokeTypeTools.isAjax(request);
        if (isAjax) {
            response.getOutputStream().write(JsonResultUtils.getErrorJsonByte());
        } else {
            response.sendRedirect("/notfound.html");
        }
        return null;
    }
}
