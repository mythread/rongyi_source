package mina.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.context.SpringContextUtil;

import com.rongyi.cms.service.JobService;

/**
 * 类MinaServerHandler.java的实现描述：TODO 类实现描� *
 * 
 * @author jiejie 2014���上午10:23:24
 */
public class MinaServerHandler extends IoHandlerAdapter {

    private static final Logger log        = LoggerFactory.getLogger("商家后台Mina Server");
    private static final String CRM_JOB_ID = "crmJobId";
    private JobService          jobService;

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        session.close(true);
    }

    /**
     * 接收crm客户端发送过来的消息
     */
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("接收到crm客户端发送过来的消息");
        if (jobService == null) {
            jobService = (JobService) SpringContextUtil.getBean("jobService");
        }
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, Object>> msg = (ArrayList<HashMap<String, Object>>) message;
        log.info("接收到crm客户端发送过来的消息");
        List<Integer> crmJobIds = new ArrayList<Integer>();
        for (HashMap<String, Object> map : msg) {
            jobService.updateSynchStatus(map);
            crmJobIds.add((Integer) map.get(CRM_JOB_ID));
        }
        session.write(crmJobIds);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

}
