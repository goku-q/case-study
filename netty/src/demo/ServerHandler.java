package demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.Buffer;
import java.nio.channels.Channel;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义handler,需要集成netty中的HandlerAdapter
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx channel上下文
     * @param msg 客户端的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //现将消息msg转化为bytebuf
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是"+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是："+ctx.channel().remoteAddress());
    }
    /**
     * 给客户端返回消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常,关闭
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
