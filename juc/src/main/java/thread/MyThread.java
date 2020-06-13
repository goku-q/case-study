package thread;

import java.util.concurrent.*;
import java.util.concurrent.Executors;

/**
 * 线程池配置策略
 * 1、cup运算比较多的，CUP核数+1，尽量保证和CUP核数相同，尽量避免线程上下文的切换
 * 2、IO处理比较多的，因IO可能会有阻塞，所以线程数因适量多点，公式：CUP核数/1-阻塞系数，阻塞系数（0.8-0.9）
 * 3、上两条只是策略模式，实际应该通过压力、并发测试，根据性能获取最合适的配置
 */
public class MyThread {

    /**
     * jdk自带的线程池创建，底层都是使用ThreadPoolExecutor类，但是不推荐使用，
     * 因为使用的都是linkedqueue，默认值integer最大值的阻塞队列来存储线程，队列太大，
     * 阿里巴巴开发手册推荐，使用threadPoolExecutor类自己创建线程池，可自己配置线程池的/7个参数，而非是executors创建的线程池
     */
    private static void jdk_executor() {
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);//固定数量的线程池
        //ExecutorService threadPool = Executors.newCachedThreadPool();//缓冲的线程池，数量根据请求而定
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        try
        {
            for (int i = 0; i < 100; i++)
            {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t办理了业务");
                    ///try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    /**
     * ThreadPoolExecutor的七个参数
     * 1、corePoolSize :核心线程数，就是初始化线程执行的数量
     * 2、maximumPoolSize :最大线程数，当核心线程数满，切队列也满时，会开启其他线程执行任务，线程上限值(包括core线程)
     * 3、keepAliveTime :保持存活时间，当访问数量下降，除core线程不再被使用时，保持的存活时间
     * 4、unit : keepAliveTime的时间单位
     * 5、BlockingQueue<Runnable></> : 阻塞队列，用于存储线程
     * 6、threadFactory :默认的线程工厂，当前线程名称等信息
     * 7、RejectedExecutionHandler : 线程池拒绝策略，总共四种：都是它的实现类，默认AbortPolicy。CallerRunsPolicy、DiscardOldestPolicy、DiscardPolicy
     */
    private static void my_executor()
    {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,
                5,
                10,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t处理了业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
    public static void main(String[] args) {
        //jdk_executor();
        my_executor();
    }


}
