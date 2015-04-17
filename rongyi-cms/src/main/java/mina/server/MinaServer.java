package mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import base.config.PropertyConfigurer;

import com.rongyi.cms.constant.Constant;

/**
 * 商家后台的mina服务
 * 
 * @author jiejie 2014年4月2日 上午10:23:09
 */
@Repository
public class MinaServer {

    private static final Logger LOG = LoggerFactory.getLogger(MinaServer.class);
    @Autowired
    private PropertyConfigurer  propertyConfigurer;

    // private int port = 8989;
    private MinaServerHandler   minaServerHandler;

    @PostConstruct
    public void init() {
        try {
            LOG.warn("-----------------启动商家后台的mina server---------------");
            start(getPort());
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
        chain.addLast("chain", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        chain.addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        minaServerHandler = new MinaServerHandler();
        acceptor.setHandler(minaServerHandler);
        acceptor.setReuseAddress(true);
        acceptor.bind(new InetSocketAddress(serverPort));
        LOG.warn(String.format("商家管理后台mina服务启动...，绑定端口：%d", serverPort));

    }

    public int getPort() {
        return Integer.valueOf((String) propertyConfigurer.getProperty(Constant.Common.MINA_SERVER_PORT));
    }

}
