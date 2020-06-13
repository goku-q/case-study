package volatiledemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile特性
 * 1、保证可见性
 * 2、不保证原子性
 * 3、禁止指令重拍
 */
public class VolatileDemo {

    volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 1、测试可见性方法
     */
    public void visibility(){
        //共享变量不加volatile
        new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"修改的num值为1");
            num =1;
        },"thread1").start();
        new Thread(()->{
            while (num!=1){
                //如果不能发现线程1修改的话，会一直死循环
            }
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"发现了线程修改了num的值");
        },"thread2").start();

        while (Thread.activeCount()>2){
            //java默认启动两个线程 main和jvm，如果大于2说明thread2未发现共享变量改变，在死循环中
        }
        System.out.println("共享变量的修改被发现了");
    }

    /**
     *2、测试无法保证原子性操作
     *3、使用原子类AtomicInteger测试能否保证原子性,原理是CAS，修改前先查询，修改的时候在对比先前查到的值与现在的是否相同如果相同则可以修改，不同则自旋
     */
    public void addadd(){
        num++;
        atomicInteger.getAndIncrement();
    }
    public void unatomic(){
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0;j<1000;j++){
                    addadd();
                }
            },"thread1"+i).start();
        }

        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println("num:最终结果=\t"+num);
        System.out.println("atomicInteger最终结果=\t"+atomicInteger.get());
    }

    public static void main(String[] args) {
        VolatileDemo volatileDemo = new VolatileDemo();
        //volatileDemo.visibility();
        volatileDemo.unatomic();
    }
}
