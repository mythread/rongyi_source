package com.rongyi.mina.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongyi.mina.common.util.SpringContextUtil;
import com.rongyi.mina.service.CmsTaskService;
import com.rongyi.mina.service.OperateMongoService;

/**
 * 类MinaServerHandler.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月2日 上午10:23:24
 */
public class MinaServerHandler extends IoHandlerAdapter {

    private static final Logger log                 = LoggerFactory.getLogger("CRM后台Mina Server");
    private static final String OPERATE_TYPE        = "operateType";
    private static final String JOB_ID              = "jobId";

    private CmsTaskService      cmsTaskService      = (CmsTaskService) SpringContextUtil.getBean("cmsTaskService");
    private OperateMongoService operateMongoService = (OperateMongoService) SpringContextUtil.getBean("operateMongoService");

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        session.close(true);
    }

    /**
     * 接收crm客户端发送过来的消息
     */
    @SuppressWarnings("unchecked")
    public void messageReceived(IoSession session, Object message) throws Exception {
        ArrayList<HashMap<String, Object>> msg = (ArrayList<HashMap<String, Object>>) message;
        List<Integer> jobIds = new ArrayList<Integer>();
        if (msg != null) {
            for (HashMap<String, Object> map : msg) {
                Integer operateType = (Integer) map.get(OPERATE_TYPE);
                if (operateType != null) {
                    if (operateType != 0) {
                        operateMongoService.operate(map);
                    } else {
                        // 处理msg
                        Integer id = cmsTaskService.insertTasks(map);
                    }
                }
                jobIds.add((Integer) map.get(JOB_ID));
            }
        }
        session.write(jobIds);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

}
