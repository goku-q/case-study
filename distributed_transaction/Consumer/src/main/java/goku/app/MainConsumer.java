package goku.app;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import goku.service.UserServiceImpl;
/**
 * 启动容器
 * @author tmac-q
 *
 */
public class MainConsumer {
	
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer.xml");
		UserServiceImpl userServiceImpl = context.getBean(UserServiceImpl.class);
		userServiceImpl.saveUser(2, "xiao");
		System.in.read();
	}
}
