package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 基于nio实现网络通讯客户端
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        //创建SocketChannel
        SocketChannel channel = SocketChannel.open();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        //设置非阻塞
        channel.configureBlocking(false);
        if(!channel.connect(new InetSocketAddress("127.0.0.1", 6666))){
            while (!channel.finishConnect()){
                System.out.println("1111111");
            }
        }
        //创建buffer
        ByteBuffer buffer = ByteBuffer.wrap("goku come from huoxing".getBytes());

        //channel写入
        channel.write(buffer);
        System.in.read();


    }
}
