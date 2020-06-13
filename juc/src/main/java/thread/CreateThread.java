package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程
 * @author tmac-q
 *
 */

//1、实现Runnable接口
class Thread_1 implements Runnable{

	@Override
	public void run() {
		System.out.println("通过实现Runnable来创建线程！");
	}
}

//2、继承Thread类
class Thread_2 extends Thread{

	@Override
	public void run() {
		System.out.println("通过继承Thread来创建线程！");
	}
}

//3、实现Callable接口
class Thread_3 implements Callable<String> {

	@Override
	public String call() throws Exception {
		System.out.println("通过实现Callable来创建线程！");
		return "callback";
	}
}

//测试
public class CreateThread {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Thread thread_1 = new Thread(new Thread_1());
		Thread thread_2 = new Thread(new Thread_2());
		FutureTask<String> futureTask = new FutureTask<>(new Thread_3());
		Thread thread_3 = new Thread(futureTask);
		thread_1.start();
		thread_2.start();
		thread_3.start();
		//通过callable创建的线程可以有返回值
		String str = futureTask.get();
		System.out.println(str);
	}
}
