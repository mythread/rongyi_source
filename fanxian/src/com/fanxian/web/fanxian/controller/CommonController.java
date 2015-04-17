package com.fanxian.web.fanxian.controller;

import java.util.Map;

import com.yue.commons.seine.web.annotations.ControllerAction;
import com.yue.commons.seine.web.servlet.result.View;
import com.yue.commons.seine.web.servlet.result.WebResult;

public class CommonController extends BaseController {

    // private static Logger log = LoggerFactory.getLogger(CommonController.class);

    @ControllerAction
    public WebResult home(Map<String, Object> model) {
        model.put("tipMsg", true);
        return new View("/home/home.htm");
    }
}
