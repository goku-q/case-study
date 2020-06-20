package rpc.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import rpc.netty.NettyServer;

/**
 * 服务端启动引导
 */
public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.initServer("127.0.0.1",6666);
    }
}
