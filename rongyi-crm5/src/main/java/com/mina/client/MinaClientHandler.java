package com.mina.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gcrm.service.IBaseService;
import com.gcrm.util.spring.SpringContextUtil;

/**
 * @author jiejie 2014年4月2日 上午10:18:58
 */
public class MinaClientHandler extends IoHandlerAdapter {

    private static final Logger                log  = LoggerFactory.getLogger("CRM_Mina Client");
    private static Lock                        lock = new ReentrantLock();
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
        log.warn("client 连接商家cms服务端成功！！！");
        // 向商家服务端发消息
        session.write(msg);
        this.ioSession = session;
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.warn("连接商家cms服务被关闭...");
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
     * 接收cms服务端发来的消息
     */
    @SuppressWarnings("unchecked")
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("-------------接收到了cms服务端发来的消息-------------");
        if (message != null && message instanceof ArrayList) {
            ArrayList<Integer> crmJobIds = (ArrayList<Integer>) message;
            if (crmJobIds != null && crmJobIds.size() > 0) {
                // 更新job状态
                @SuppressWarnings("rawtypes")
                IBaseService jobService = (IBaseService) SpringContextUtil.getBean("baseService");
                String hql = "update CrmJob set jobStatus = 1 where id in (" + StringUtils.join(crmJobIds, ",") + ")";
                jobService.updateByHql(hql);
                log.warn("---------------成功更新crm job状态数据至1-------------");
            }
        }
        session.close(true);
    }

    public void messageSent(IoSession session, Object message) throws Exception {

    }

}
