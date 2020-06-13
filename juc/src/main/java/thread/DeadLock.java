package thread;

import java.util.concurrent.TimeUnit;
/**
 * 普通锁
 */
class GeneralLock implements Runnable
{
    public synchronized void lock()
    {
        System.out.println(Thread.currentThread().getName()+"\t执行中，锁住了代码快");
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        lock();
        System.out.println(Thread.currentThread().getName()+"\t执行完毕");
    }
}

/**
 * 代码实现死锁
 */
class DeadLockResource implements Runnable
{
    private String lockA;
    private String lockB;

    public DeadLockResource(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA)
        {
            //线程1先获得了lockA锁，睡眠1秒还没有获得lockB锁时，线程2又进来了，获得了lockB锁，结果就成了死锁
            System.out.println(Thread.currentThread().getName()+"\t"+"持有锁"+lockA+"尝试获得"+lockB);
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

            synchronized (lockB)
            {
                System.out.println(Thread.currentThread().getName()+"\tcome in");
            }
        }
    }
}

public class DeadLock {
    public static void main(String[] args) {
        //GeneralLock lock = new GeneralLock();
        //new Thread(lock,"Thread1").start();
        //new Thread(lock,"Thread2").start();
        System.out.println("**************************************");
        new Thread(new DeadLockResource("lockA","lockB"),"Thread1").start();//线程1执行先获得lockA
        new Thread(new DeadLockResource("lockB","lockA"),"Thread2").start();//线程2执行先获得lockB

    }
}
