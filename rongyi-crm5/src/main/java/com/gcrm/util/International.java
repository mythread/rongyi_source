package com.gcrm.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class International implements Filter {
    public void destroy() {
        // TODO Auto-generated method stub  

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        RequestDispatcher ds = request
                .getRequestDispatcher("/jsp/crm/totalfilter.action");
        request.setAttribute("jsp", httpRequest.getServletPath());
        request.setAttribute("param", httpRequest.getQueryString());
        ds.forward(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub  

    }
}