package com.fanxian.web.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 请求的类型
 * 
 * @author wanghai 2011-10-18 上午10:48:19
 */
public class InvokeTypeTools {

    public static final String INVOKE_TYPE = "Invoke-Type";

    public static boolean isAjax(HttpServletRequest request) {
        String type = request.getHeader(INVOKE_TYPE);
        return InvokeType.isAjax(type);
    }

    public static InvokeType getInvokeType(HttpServletRequest request) {
        String type = request.getHeader(INVOKE_TYPE);
        return InvokeType.getEnum(type);
    }

    public static enum InvokeType {

        AJAX, HTTP;

        public static boolean isAjax(String type) {
            return AJAX.name().equalsIgnoreCase(type);
        }

        public static boolean isHttp(String type) {
            return HTTP.name().equalsIgnoreCase(type);
        }

        public static InvokeType getEnum(String type) {
            for (InvokeType t : values()) {
                if (StringUtils.equalsIgnoreCase(t.name(), type)) {
                    return t;
                }
            }
            return null;
        }

    }

}
