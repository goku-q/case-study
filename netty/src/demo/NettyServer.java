package demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /**
             * 创建bossgroup,只用来监听连接事件
             * 和workergroup用来监听业务事件
             * 都是一直循环监听，如名字 loop
             */
            //创建并设置启动引导
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置引导配置参数
            bootstrap.group(bossGroup,workerGroup)//配置两个线程组
                    .channel(NioServerSocketChannel.class)//设置服务端的serversocketchannel
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活跃状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个处理handler
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            System.out.println("the server is ready.........");
            //绑定端口
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            //监听关闭channel
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("finally代码块执行了");
        }
    }
}
