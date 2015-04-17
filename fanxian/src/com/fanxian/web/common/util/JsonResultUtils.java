package com.fanxian.web.common.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yue.commons.lang.Argument;
import com.yue.commons.seine.web.servlet.result.mime.DefaultJsonResult;
import com.yue.commons.seine.web.servlet.result.mime.JsonResult;
import com.yue.commons.seine.web.util.json.JsonUtils;

/**
 * 通用Ajax请求的JSON 结果
 * 
 * @author wanghai 2011-10-11 下午3:46:14
 */
public class JsonResultUtils {

    private static final String UTF_8 = "utf-8";
    private static String       needLoginJson;
    private static byte[]       needLoginJsonByte;
    private static String       forbiddenJson;
    private static byte[]       forbiddenJsonByte;
    private static String       errorJson;
    private static byte[]       errorJsonByte;

    public static JsonResult success() {
        return success(null, null);
    }

    public static JsonResult success(Object data) {
        return success(data, null);
    }

    public static JsonResult success(Object data, boolean escape) {
        return success(data, null, escape);
    }

    public static JsonResult success(Object data, String message) {
        return buildJsonResult(ResultCode.SUCCESS, data, message);
    }

    public static JsonResult success(Object data, String message, boolean escape) {
        return buildJsonResult(ResultCode.SUCCESS, data, message, escape);
    }

    public static JsonResult needLoJsonResult() {
        return needLogin(null, null);
    }

    public static JsonResult needLogin(Object data) {
        return needLogin(data, null);
    }

    public static JsonResult needLogin(Object data, String message) {
        return buildJsonResult(ResultCode.NEED_LOGIN, data, message);
    }

    // /////////////////////////////////////////

    public static String getNeedLoginJson() {
        if (StringUtils.isEmpty(needLoginJson)) {
            try {
                needLoginJson = initResult(ResultCode.NEED_LOGIN);
                needLoginJsonByte = needLoginJson.getBytes(UTF_8);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return needLoginJson;
    }

    public static byte[] getNeedLoginJsonByte() {
        if (needLoginJsonByte == null) {
            try {
                needLoginJson = initResult(ResultCode.NEED_LOGIN);
                needLoginJsonByte = needLoginJson.getBytes(UTF_8);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return needLoginJsonByte;
    }

    public static String getForbiddenJson() {
        if (StringUtils.isEmpty(forbiddenJson)) {
            try {
                forbiddenJson = initResult(ResultCode.FORBIDDEN);
                forbiddenJsonByte = forbiddenJson.getBytes(UTF_8);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return forbiddenJson;
    }

    public static byte[] getForbiddenJsonByte() {
        if (forbiddenJsonByte == null) {
            try {
                forbiddenJson = initResult(ResultCode.FORBIDDEN);
                forbiddenJsonByte = forbiddenJson.getBytes(UTF_8);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return forbiddenJsonByte;
    }

    public static String getErrorJson() {
        if (StringUtils.isEmpty(errorJson)) {
            try {
                errorJson = initResult(ResultCode.ERROR);
                errorJsonByte = errorJson.getBytes(UTF_8);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return errorJson;
    }

    public static byte[] getErrorJsonByte() {
        if (errorJsonByte == null) {
            try {
                errorJson = initResult(ResultCode.ERROR);
                errorJsonByte = errorJson.getBytes(UTF_8);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return errorJsonByte;
    }

    private static String initResult(ResultCode resultCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("result", resultCode.getValue());// 本次请求是否成功
        params.put("message", getMessage(resultCode, null));// 用户封装信息，典型的是检验出错信息
        params.put("data", StringUtils.EMPTY);// 本次请求需要返回的数据
        try {
            return JsonUtils.object2Json(params);
            // jsonByte = json.getBytes(UTF_8);
        } catch (Exception e) {
        }
        return null;
    }

    // //////////////////////////////////////

    public static JsonResult error() {
        return error(null, null);
    }

    public static JsonResult error(String message) {
        return error(null, message);
    }

    public static JsonResult error(String message, boolean escape) {
        return error(null, message, escape);
    }

    public static JsonResult error(Map<String, ? extends Object> data) {
        return error(data, null);
    }

    public static JsonResult error(Map<String, ? extends Object> data, boolean escape) {
        return error(data, null);
    }

    public static JsonResult error(Object data, String message) {
        return buildJsonResult(ResultCode.ERROR, data, message);
    }

    public static JsonResult error(Object data, String message, boolean escape) {
        return buildJsonResult(ResultCode.ERROR, data, message, escape);
    }

    public static JsonResult submitted(Object data) {
        return buildJsonResult(ResultCode.SUBMITED, data, null);
    }

    public static JsonResult forbidden() {
        return buildJsonResult(ResultCode.FORBIDDEN, null, null);
    }

    public static JsonResult forbidden(Object data, String message) {
        return buildJsonResult(ResultCode.FORBIDDEN, data, message);
    }

    public static JsonResult forbidden(Object data, String message, boolean escape) {
        return buildJsonResult(ResultCode.FORBIDDEN, data, message, escape);
    }

    public static JsonResult buildJsonResult(ResultCode code, Object data, String message) {
        return buildJsonResult(code, data, message, true);
    }

    public static JsonResult buildJsonResult(ResultCode code, Object data, String message, boolean escape) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("result", code.getValue());// 本次请求是否成功
        params.put("message", getMessage(code, message));// 用户封装信息，典型的是检验出错信息
        params.put("data", data == null ? StringUtils.EMPTY : data);// 本次请求需要返回的数据
        return new DefaultJsonResult(params, escape);
    }

    private static String getMessage(ResultCode code, String message) {
        if (Argument.isNotBlank(message)) {
            return message;
        }
        //
        switch (code) {
            case SUCCESS:
                return "操作成功";
            case ERROR:
                return "操作失败";
            case NEED_LOGIN:
                return "需要登录";
            case SUBMITED:
                return "表单重复提交";
            case FORBIDDEN:
                return "权限不够";
            default:
                return null;
        }
    }

}
