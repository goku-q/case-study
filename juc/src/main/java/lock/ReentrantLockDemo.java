package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * @author tmac-q
 *
 */
public class ReentrantLockDemo implements Runnable{
	ReentrantLock lock = new ReentrantLock();
	static int num = 10;//共享资源
    //用法一：同步方法（锁的是对象）
    private void sync(){
    	lock.lock();
        while (num>0)
        {
            if(num>0)
            {
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace();}//模拟线程因各种原因的卡顿100毫秒
                System.out.println(Thread.currentThread().getName()+"-------------num="+num);
                num--;
            }

        }
        lock.unlock();//一般放入finally块中
    }
    
    @Override
	public void run() {
		sync();
	}
    public static void main(String[] args) {
		ReentrantLockDemo lockDemo = new ReentrantLockDemo();
		Thread thread = new Thread(lockDemo);
		Thread thread1 = new Thread(lockDemo);
		thread.start();
		thread1.start();
	}
}
