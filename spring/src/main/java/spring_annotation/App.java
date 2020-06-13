package spring_annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring_annotation.config.AppConfig;

public class App {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);//加载注解的配置类
		Server server = applicationContext.getBean(Server.class);
		server.test();
	}
}
