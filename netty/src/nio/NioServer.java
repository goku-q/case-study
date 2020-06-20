package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于nio实现网络通信服务端
 */
public class NioServer {
    public static void main(String[] args) throws Exception {
        //创建一个ServerSocketChannel，类似于bio中的ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建一个Selector，来管理Channel
        Selector selector = Selector.open();
        //将serverSocketChannel绑定一个地址及端口，默认localhost
        serverSocketChannel.bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);//设置为非阻塞
        //将serverSocketChannel注册到selector中并绑定事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //监听
        while (true){

            if(selector.select(1000)==0){
                System.out.println("等待两秒钟，无客户端连接");
                continue;
            }
            //获取selectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                //获取key
                SelectionKey key = keyIterator.next();
                //如果是连接事件
                if(key.isAcceptable()){
                    System.out.println("test");
                    //获取SocketChannel，本身是阻塞，但是因为事件触发，立刻执行，所以不存在nio的一直阻塞
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册进Selector,并绑定缓冲
                    socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                    System.out.println(socketChannel.hashCode());
                    System.out.println("客户端连接成功！");
                }
                //如果是读事件
                if(key.isReadable()){
                    //如果是读事件，通过key获得channel
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    //通过key获取buffer
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    //读取
                    socketChannel.read(byteBuffer);
                    System.out.println("from client"+new String(byteBuffer.array()));
                }
                keyIterator.remove();//必须将删除，否则会出现重复操作
            }
        }
    }
}
