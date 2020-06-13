package spring_aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 模拟数据库添加操作
 * @author tmac-q
 *
 */
@Component
public class Bank {
	
	public void save() {
		System.out.println("存款成功");
	}
	
	public static void main(String[] args) {
		//通过配置文件xml实现
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-aop.xml");
		//通过注解实现
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
		Bank bank =(Bank)context.getBean(Bank.class);
		
		bank.save();
	}
}
