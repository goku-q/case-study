package lock;
/**
 * synchronized关键字的四种用法
 * 1、作用于方法，锁对象 --sync1()
 * 2、作用于代码块，锁对象--sync2()
 * 3、作用于代码块，锁类--sync3()
 * 4、作用于静态方法，锁类--sync4()
 */
class Sync implements Runnable
{
    static int num = 10;//共享资源
    //用法一：同步方法（锁的是对象）
    private void sync(){
        while (num>0)
        {
            if(num>0)
            {
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace();}//模拟线程因各种原因的卡顿100毫秒
                num--;
                System.out.println("num="+num);
            }

        }
    }

    //同步代码块(锁的也是括号里的对象)
    private void sync2(){
        while (num>0)
        {
            synchronized (this){
                if(num>0)
                {
                    try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace();}//模拟线程因各种原因的卡顿100毫秒
                    num--;
                    System.out.println("num="+num);
                }
            }
        }
    }
    //锁的对象是类，与对象无关，只要是访问次类都枷锁
    private void sync3(){
        while (num>0)
        {
            synchronized (Sync.class){
                if(num>0)
                {
                    try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace();}//模拟线程因各种原因的卡顿100毫秒
                    num--;
                    System.out.println("num="+num);
                }
            }
        }
    }

    //锁的对象也是类，作用在static方法中
    public static synchronized void sync4(){
        while (num>0)
        {
                if(num>0)
                {
                    try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace();}//模拟线程因各种原因的卡顿100毫秒
                    num--;
                    System.out.println("num="+num);
                }
        }
    }

    @Override
    public void run() {
        //sync();
        //sync2();
        //sync3();
        //sync4();
    }
}
public class SynchronizedDemo {
    public static void main(String[] args) {
        Sync sync = new Sync();
        //Sync sync2 = new Sync();//用于测试-锁类sync3、sync4
        //new Thread(sync2).start();//用于测试-锁类
        new Thread(sync).start();
        new Thread(sync).start();

    }
}
