package bio;

import io.netty.util.NettyRuntime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO实现网络连接，不创建客户端，使用终端telnet连接
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        //创建线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //创建一个serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动成功！");
        //监听、等待
        while (true){
            System.out.println("服务等待连接。。。");
            //socket等待服务器连接
            Socket socket = serverSocket.accept();
            //连接成功后启线程执行
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //和客户端通讯
                    handler(socket);
                }
            });
        }
    }

    private static void handler(Socket socket){
        byte[] bytes = new byte[1024];
        try {
            //将数据读取进io流中
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("线程"+Thread.currentThread().getName()+"等待read");
            //循环read客户端发来的数据
            while (true){
                int read = inputStream.read(bytes);
                if (read!=-1) {
                    System.out.println(new String(bytes));
                    outputStream.write(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭和客户端的连接
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
