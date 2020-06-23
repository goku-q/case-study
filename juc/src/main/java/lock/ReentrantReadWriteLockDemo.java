package lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 
 * @author tmac-q
 *
 */
public class ReentrantReadWriteLockDemo implements Runnable {
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	// 读读锁不互斥，两个线程会交叉执行
	public void readread() {
		try {
			lock.readLock().lock();// 上读锁
			System.out.println(Thread.currentThread().getName() + "------start");
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName() + "------end");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();// 释放读锁
		}
	}

	// 写写互斥，两个线程会各自同步执行，读写也互斥
	public void writewrite() {
		try {
			lock.writeLock().lock();// 上写锁
			System.out.println(Thread.currentThread().getName() + "------start");
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName() + "------end");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();// 释放写锁
		}
	}

	@Override
	public void run() {
		readread();
		writewrite();
	}

	public static void main(String[] args) {
		ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();
		new Thread(demo).start();
		new Thread(demo).start();
	}
}
