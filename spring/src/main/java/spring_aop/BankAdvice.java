package spring_aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.log.LogFactory;

/**
 * 统一处理日志
 * @author tmac-q
 *
 */
@Component
@Aspect//声明为一个切面类
public class BankAdvice {
	//声明一个切点，与配置文件一样使用aspectj的语法格式
	/**
	 * execution表达式   * *..*.*(..)
	 * 	访问修饰符可以忽略，*代表通配符：第一个*是返回值，第二个*.. 是当前包及其子包（如果不用这种就是有几个包写几个例如，*.*.*==com.goku.q），第三个*是类，第四个*.*是类名及方法名，第五个(..)是方法参数随意
	 */
	@Pointcut("execution(public * spring_aop.Bank.save())")
	public void save() {}
	/**
	 * 以下为增强方法，及关联切点
	 */
	@Before("save()")
	public void login() {
		System.out.println("前置增强：安全登录");
	}
	@After("save()")
	public void exit() {
		System.out.println("后置增强：安全退出");
	}
	@Around("save()")
	public void around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("环绕增强：开始操作");
		pjp.proceed();
		System.out.println("环绕增强：结束操作");
	}
}
