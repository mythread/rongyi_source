package com.rongyi.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 容易CRM的mina服务
 * 
 * @author jiejie 2014年4月2日 上午10:23:09
 */
public class MinaServer {

    private static final Logger LOG  = LoggerFactory.getLogger("CRM后台Mina_Server");
    private int                 port = 8991;
    private MinaServerHandler   minaServerHandler;

    public void init() {
        try {
            start(port);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    // 启动服务
    public void start(int serverPort) throws IOException {
        // 创建一个非阻塞的的server端socket,用NIO
        SocketAcceptor acceptor = new NioSocketAcceptor();
        // 创建接收数据的过滤器
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        // 1: TextLineCodecFactory设置这个过滤器一行一行(/r/n)的读取数据
        // 2: ObjectSerializationCodecFactory一般接收的是对象等形象,以对象形式读取
        // chain.addLast("chain", new ProtocolCodecFilter(new TextLineCodecFactory()));

        chain.addLast("chain", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // 多线程
        chain.addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        minaServerHandler = new MinaServerHandler();
        acceptor.setHandler(minaServerHandler);
        acceptor.setReuseAddress(true);
        acceptor.bind(new InetSocketAddress(serverPort));
        LOG.warn(String.format("容易CRM mina服务启动...，绑定端口：%d", serverPort));
    }

    public static void main(String[] args) {
        MinaServer server = new MinaServer();
        server.init();
    }
}
