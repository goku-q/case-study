package rpc.server;

import com.sun.jmx.snmp.SnmpMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.service.HelloWorld;
import rpc.service.HelloWorldImpl;

/**
 * 服务端控制器
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 可读
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg="+msg);
        //自定义协议，rpc#helloworld#args
        if (msg.toString().startsWith("rpc#")){//如果符合rpc协议
            //取调用类名
            String className = msg.toString().substring(msg.toString().indexOf("#")+1, msg.toString().lastIndexOf("#"));//协议类名
            System.out.println("需要远程调用的className："+className);
            String args = msg.toString().substring(msg.toString().lastIndexOf("#")+1);//协议中参数
            System.out.println("远程调用传入的参数："+args);
            String result =null;
            if(className.equals("helloworld")){
                 result = new HelloWorldImpl().say(args);
            }
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
