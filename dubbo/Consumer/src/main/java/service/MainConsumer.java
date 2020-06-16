package service;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainConsumer {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer.xml");
		OrderService orderService = context.getBean(OrderService.class);
		orderService.showUser();
		System.in.read();
	}
}
