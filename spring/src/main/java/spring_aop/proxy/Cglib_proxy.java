package spring_aop.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 使用Cglib实现动态代理，代理对象（实现MethodInterceptor接口），目标对象不需要有接口实现
 * @author tmac-q
 *
 */
public class Cglib_proxy implements MethodInterceptor{
	
	public Object createProxy(Object object) {
		//类似与jdk的Proxy类
		Enhancer enhancer = new Enhancer();
		//设置目标（需要代理）类
		enhancer.setSuperclass(T_mac.class);
		//设置回调，就是代理类
		enhancer.setCallback(this);
		//创建代理对象
		return enhancer.create();
	}

	//拦截方法，在此方法中对代理方法增强
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("使用cglib对tmac方法进行了增强");
		Object result = proxy.invokeSuper(obj, args);//目标方法调用
		return result;
	}
	
	public static void main(String[] args) {
		Cglib_proxy cglib_proxy = new Cglib_proxy();
		Object proxy = cglib_proxy.createProxy(new T_mac());
		T_mac tmac = (T_mac)proxy;
		tmac.player();
		
	}

}
