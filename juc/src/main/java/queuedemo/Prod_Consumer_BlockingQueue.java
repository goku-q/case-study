package queuedemo;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用队列实现生产者消费者
 * 使用阻塞队列实现，不用枷锁
 * 使用原子类确保多线程的原子性
 */

/**
 * 共享资源
 */
class SharedResource
{
    private volatile boolean FLAG = true;//默认开启生产 消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;

    public SharedResource(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /**
     * 生产者
     */
    public void producer() throws Exception {
        String data = null;
        while (FLAG)
        {
            data = atomicInteger.incrementAndGet()+"";
            boolean resultFlag;
            resultFlag = blockingQueue.offer(data);
            if(resultFlag)
            {
                System.out.println(Thread.currentThread().getName()+"生产\t"+data+"成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"生产\t"+data+"失败");
            }
            Thread.sleep(1000);
        }
        System.out.println("生产活动结束！");
    }
    /**
     * 消费者
     */
    public void consumer() throws Exception
    {
        String data = null;
        boolean resoultFlag;
        while (FLAG)
        {
            data = blockingQueue.poll();
            if(null==data || data.equals(""))
            {
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"消费\t"+data+"失败,退出消费");
                System.out.println();
                System.out.println();
                System.out.println();
            }
            System.out.println(Thread.currentThread().getName()+"消费\t"+data+"成功");
            Thread.sleep(1000);
        }
        System.out.println("消费活动结束！");
    }
    /**
     * 停止活动
     */
    public void stop()
    {
        FLAG=false;
    }
}

public class Prod_Consumer_BlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        SharedResource sharedResource = new SharedResource(new ArrayBlockingQueue(10));
            new Thread(()->{
                try {
                    sharedResource.producer();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            },"producer").start();

            new Thread(()->{
                try {
                    sharedResource.consumer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"consumer").start();

        Thread.sleep(6000);
        System.out.println("6秒后终止生产消费活动");
        sharedResource.stop();
        System.out.println();
        System.out.println();
        System.out.println();

    }
}
