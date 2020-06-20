package demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 客户端处理器,需要继承netty的handleradapter
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 连接就会执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server,i am client",CharsetUtil.UTF_8));
    }

    /**
     * 可读
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器发送的消息是"+buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址是："+ctx.channel().remoteAddress());
    }
}
