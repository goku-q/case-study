package rpc.netty;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import rpc.client.ClientHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于netty的客户端
 */
public class NettyClient {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static ClientHandler clientHandler;
    //通过反射，动态代理
    public static Object getBean(final Class tarClass,final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{tarClass},(prox,method,args)->{
                    if(clientHandler==null){
                        client0();
                    }
                    clientHandler.setPara(providerName+args[0]);
                    System.out.println("通过callable发送信息");
                    return executor.submit(clientHandler).get();
                });
    }

    private static void client0(){
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        clientHandler = new ClientHandler();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(clientHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",6666).sync();
            System.out.println("客户端已开启。。。。。。监听中");
            //channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
