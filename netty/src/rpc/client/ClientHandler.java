package rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * 客户端的控制器
 */
public class ClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext ctx;
    private String para;
    private String result;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result=msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public synchronized Object call() throws Exception {
        ctx.writeAndFlush(para);
        wait();
        return result;
    }
    public void setPara(String para){
        this.para=para;
    }
}
