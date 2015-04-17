package com.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * httpclient工具类
 * 
 * @author jiejie 2014年4月12日 下午9:30:44
 */
public class HttpClientUtils {

    private static final Logger                       log               = LoggerFactory.getLogger(HttpClientUtils.class);
    private static MultiThreadedHttpConnectionManager connectionManager = null;
    private static HttpClient                         client            = null;

    public static synchronized void init() {
        if (connectionManager != null) {
            return;
        }
        connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(10000);
        params.setSoTimeout(20000);
        params.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 1000);
        params.setMaxTotalConnections(10000);
        connectionManager.setParams(params);
        client = new HttpClient(connectionManager);
        client.getParams().setParameter("http.protocol.max-redirects", 3);
    }

    /**
     * @param method
     * @param tryTimes
     * @param soTimeoutMill
     * @return
     */
    private static InputStream getResponseBodyAsStream(HttpMethod method, Integer tryTimes, Integer soTimeoutMill) {
        init();
        if (tryTimes == null) {
            tryTimes = 1;
        }
        if (soTimeoutMill == null) {
            soTimeoutMill = 20000;
        }
        InputStream in = null;
        method.getParams().setSoTimeout(soTimeoutMill);
        for (int i = 0; i < tryTimes; i++) {
            try {
                int responseCode = client.executeMethod(method);
                if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    in = method.getResponseBodyAsStream();
                    return in;
                }
                log.error(String.format("getResponseBodyAsString failed, responseCode: %d, should be 200, 301, 302",
                                        responseCode));
            } catch (Exception e) {
                log.error("getResponseBodyAsString failed", e);
            }
        }
        return null;
    }

    public static byte[] getResponseBodyAsByte(HttpMethod method) {
        InputStream inputStream = null;
        try {
            inputStream = getResponseBodyAsStream(method, null, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            method.releaseConnection();
        }
        return new byte[] {};
    }

}
