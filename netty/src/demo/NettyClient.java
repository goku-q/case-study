package demo;

import com.sun.javafx.sg.prism.NGEllipse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty实现客户端
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //创建事件循环监听组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建启动引导,服务端是serverbootstrap，客户端是bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //配置bootstrap信息
            bootstrap.group(group)//设置线程组
                    .channel(NioSocketChannel.class)//设置channel
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //客户端处理handler
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            System.out.println("the client is ready.......");
            //连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            //监听通道关闭
            channelFuture.channel().closeFuture().sync();
        }finally {
            //优雅关闭
            group.shutdownGracefully();
        }
    }
}
