package com.rongyi.monitor.web.controller;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rongyi.monitor.biz.domain.TerminalScheduleInfo;
import com.rongyi.monitor.biz.service.TerminalScheduleService;

/**
 * 类TraceController.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月10日 上午11:26:36
 */
@Controller
public class TraceController {

    private static final Logger     LOG = LoggerFactory.getLogger(TraceController.class);
    @Autowired
    private TerminalScheduleService terminalScheduleService;

    @RequestMapping("/terminal/trace.htm")
    public void trace(@RequestParam
    String info) {
        if (StringUtils.isEmpty(info)) {
            return;
        }
        JSONObject json = null;
        try {
            json = JSONObject.fromObject(info);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            LOG.error("convert jsonobject error! info: {}", info);
        }
        if (json == null) {
            return;
        }
        TerminalScheduleInfo terminalInfo = (TerminalScheduleInfo) JSONObject.toBean(json, TerminalScheduleInfo.class);
        if (terminalInfo != null) {
            boolean update = terminalScheduleService.updateGmtModified(terminalInfo);
            if (!update) {
                terminalScheduleService.insert(terminalInfo);
            }
        }
    }

}
