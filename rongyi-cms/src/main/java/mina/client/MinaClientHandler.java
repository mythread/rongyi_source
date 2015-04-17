package mina.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.context.SpringContextUtil;

import com.rongyi.cms.service.JobService;

/**
 * @author jiejie 2014年4月2日 上午10:18:58
 */
public class MinaClientHandler extends IoHandlerAdapter {

    private static final Logger                log = LoggerFactory.getLogger("商家Mina Client");
    private ArrayList<HashMap<String, Object>> msg;
    private IoSession                          ioSession;

    /**
     * 是否建立了连接
     */
    public boolean isConnected() {
        if (ioSession != null) {
            return ioSession.isConnected();
        }
        return false;
    }

    public MinaClientHandler(ArrayList<HashMap<String, Object>> msg) {
        this.msg = msg;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {

    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.warn("client 与crm服务建立连接...");
        // 向crm服务端发消息
        session.write(msg);
        this.ioSession = session;
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.warn("连接crm服务被关闭...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        // TODO Auto-generated method stub
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        session.close(true);
    }

    /**
     * 接收crm服务端发来的消息
     */
    public void messageReceived(IoSession session, Object message) throws Exception {
        if (session == null) {
            return;
        }
        if (message != null && message instanceof ArrayList) {
            @SuppressWarnings("unchecked")
            ArrayList<Integer> msg = (ArrayList<Integer>) message;
            // 更新job状态
            JobService jobService = (JobService) SpringContextUtil.getBean("jobService");
            jobService.batchUpdateJobStatus(msg);
            log.warn("crm 服务端已成功接收到数据，且cms job数据状态已被更新为【1】");
        }
        session.close(true);
    }

    public void messageSent(IoSession session, Object message) throws Exception {

    }

}
