package com.fanxian.web.fanxian.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;

import com.yue.commons.core.CommonServiceLocator;
import com.yue.commons.log.LoggerFactoryWrapper;
import com.yue.commons.seine.web.annotations.ControllerAction;
import com.yue.commons.seine.web.servlet.result.View;
import com.yue.commons.seine.web.servlet.result.WebResult;
import com.yue.commons.seine.web.servlet.result.mime.NormalWebResult;

public class OKController extends BaseController {

    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private Logger               logger      = LoggerFactoryWrapper.getLogger(OKController.class);

    @ControllerAction
    public WebResult ok(Map<String, Object> model) throws Exception {
        if (initialized.compareAndSet(false, true)) {
            // 初始化spring容器
            CommonServiceLocator.getApplicationContext();
        }
        logger.error("OKController is starting");
        return new NormalWebResult("ok OK");
    }

    @ControllerAction
    public WebResult notfound(Map<String, Object> model) throws Exception {
        return new View("/error/404_run.vm");
    }
}
