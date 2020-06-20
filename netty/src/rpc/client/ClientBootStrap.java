package rpc.client;

import rpc.netty.NettyClient;
import rpc.service.HelloWorld;

/**
 * 客户端启动引导
 */
public class ClientBootStrap {
    public static void main(String[] args) {
        HelloWorld helloWorld = (HelloWorld)NettyClient.getBean(HelloWorld.class, "rpc#helloworld#");
        String hello = helloWorld.say("hello");
        System.out.println(hello);
    }
}
