package spring_aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * java实现动态代理，代理对象只需要实现jdk提供的InvocationHandler接口，目标（被代理）对象需要有实现接口，否则无法实现动态
 * 
 * @author tmac-q
 *
 */
public class Jdk_Proxy implements InvocationHandler {

	private Object target;

	public Jdk_Proxy(Object target) {
		super();
		this.target = target;
	}

	// 可以在此进行方法的增强，这个方法感觉就是通过反射调用目标对象的方法，并在此增强操作等
	public Object invoke(Object proxy, // 代理对象
			Method method, // 当前执行的方法
			Object[] args)// 执行当前方法所需要的参数
			throws Throwable {
		System.out.println("通过jdk动态代理实现tmac方法的增强");
		Object object = method.invoke(target, args);
		return object;
	}

	// 创建代理对象
	public Object createProxy() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);//第二个参数为获取目标对象的所有现实接口
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, Throwable {
		Jdk_Proxy jdk_Proxy = new Jdk_Proxy(new T_mac());
		Object proxy = jdk_Proxy.createProxy();//获取代理对象
		Nba nba = (Nba) proxy;
		nba.player();//执行代理增强方法方法
	}
}
