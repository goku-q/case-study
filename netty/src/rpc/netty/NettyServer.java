package rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import rpc.server.ServerHandler;

/**
 * 初始化nettyserver服务端
 */
public class NettyServer {
    public static void initServer(String hostName , Integer port){
        server0(hostName,port);
    }


    private static void server0(String hostName , Integer port){
        //创建boss和worker组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new  NioEventLoopGroup();
        //创建启动引导
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)//添加分组
                    .channel(NioServerSocketChannel.class)//添加监听的通道
                    .childHandler(new ChannelInitializer<SocketChannel>() {//添加worker的的处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());//netty的解码器
                            pipeline.addLast(new StringEncoder());//netty的编码器
                            pipeline.addLast(new ServerHandler());//添加相应自定义的处理器
                        }
                    });
            //绑定端口并同步启动
            ChannelFuture channelFuture = bootstrap.bind(hostName, port).sync();
            System.out.println("服务器端已开启。。。。监听中");
            //监听关闭通道
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
