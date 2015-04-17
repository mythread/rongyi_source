package com.rongyi.monitor.common;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 类MessageManger.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月10日 下午2:09:18
 */
public class MessageManger {

    private static MessageManger instance = new MessageManger();
    private static final String  SMS_URL  = "http://service6.baiwutong.com:8080/sms_send2.do";

    private MessageManger() {
    }

    public static MessageManger getInstance() {
        return instance;
    }

    /**
     * 发送短信
     * 
     * @param phone :手机号
     * @param message :短信内容
     */
    public String sendSmsMessage(String phone, String message) {
        NameValuePair[] params = new NameValuePair[] { new NameValuePair("corp_id", "6e1r001"),
                new NameValuePair("corp_pwd", "6e1r001"), new NameValuePair("corp_service", "106550939yd"),
                new NameValuePair("mobile", phone), new NameValuePair("msg_content", message),
                new NameValuePair("corp_msg_id", ""), new NameValuePair("ext", "86") };
        HttpClient httpclient = new HttpClient();
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(30000);
        GetMethod getMethod = new GetMethod(SMS_URL);
        getMethod.setQueryString(params);
        try {
            httpclient.executeMethod(getMethod);
            return getMethod.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            getMethod.releaseConnection();
        }
    }

}
