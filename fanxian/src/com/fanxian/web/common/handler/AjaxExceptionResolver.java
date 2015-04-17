package com.fanxian.web.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.fanxian.web.common.util.InvokeTypeTools;
import com.yue.commons.seine.web.handler.ComponentMappingExceptionResolver;

public class AjaxExceptionResolver extends ComponentMappingExceptionResolver {

    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex) {
        boolean isAjax = InvokeTypeTools.isAjax(request);
        if (isAjax) {
            return null;
        }
        return super.doResolveException(request, response, handler, ex);
    }
}
