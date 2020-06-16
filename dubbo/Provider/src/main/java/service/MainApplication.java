package service;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动容器，并注册发布
 * @author tmac-q
 *
 */
public class MainApplication {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");
		context.start();
		System.in.read();
		
	}
}
