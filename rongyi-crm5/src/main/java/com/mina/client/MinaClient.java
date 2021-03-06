package com.mina.client;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商家后台mina客户端
 * 
 * @author jiejie 2014年4月2日 上午10:17:35
 */
public class MinaClient {

    private static final Logger log = LoggerFactory.getLogger(MinaClient.class);
    private String              serverIp;
    private int                 serverPort;
    private MinaClientHandler   clientHandler;

    public MinaClient(String serverIp, int serverPort, ArrayList<HashMap<String, Object>> msg, boolean wait) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        clientHandler = connectRYServerByMina(serverIp, serverPort, msg, wait);
    }

    // 连接商家后台的服务端
    private MinaClientHandler connectRYServerByMina(String serverIp, int serverPort,
                                                    ArrayList<HashMap<String, Object>> msg, boolean wait) {
        log.warn("开始连接CRM的mima服务器。。。。。。");
        NioSocketConnector connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        chain.addLast("myChain", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // 设置处理的类
        MinaClientHandler clientHandler = new MinaClientHandler(msg);
        connector.setHandler(clientHandler);
        connector.setConnectTimeoutMillis(300000);
        // 开始连接服务器
        ConnectFuture cf = connector.connect(new InetSocketAddress(serverIp, serverPort));

        // 等待连接结束
        if (wait) {
            cf.awaitUninterruptibly();
            try {
                cf.getSession().getCloseFuture().awaitUninterruptibly();
                connector.dispose();
            } catch (Exception e) {
                log.error(e.getMessage());
                connector.dispose();
            }
        }
        return clientHandler;
    }

    /**
     * 检查连接服务端是否成功
     */
    @SuppressWarnings("static-access")
    private void checkConnectServer(ArrayList<HashMap<String, Object>> msg, boolean wait) {
        // 多次连接服务器
        for (int i = 0; i < 3; i++) {
            if (clientHandler != null && !clientHandler.isConnected()) {
                try {
                    log.warn("重新连接crm服务，并且等待连接... sleep 1s");
                    Thread.currentThread().sleep(1000);
                    clientHandler = connectRYServerByMina(serverIp, serverPort, msg, wait);
                    if (clientHandler.isConnected()) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!clientHandler.isConnected()) {
            log.warn("重新连接crm服务3次均失败。。。请检查服务是否启动。。。");
        }
    }

}
