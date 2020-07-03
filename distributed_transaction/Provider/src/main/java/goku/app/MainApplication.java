package goku.app;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动容器，mq最终一致性控制事务
 * @author tmac-q
 *
 */
public class MainApplication {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");
		System.in.read();
		
	}
}
