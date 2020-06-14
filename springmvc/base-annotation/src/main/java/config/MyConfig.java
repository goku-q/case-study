package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import interceptor.MyInterceptor;

/**
 * 基于纯注解实现SpringMVC,需要实现WebMvcConfigurer接口，接口含各种mvc的配置
 * @author tmac-q
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan({"config","controller","dao","service","interceptor"})
public class MyConfig implements WebMvcConfigurer{
	

	//注入视图解析器
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setSuffix(".jsp");
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setExposeContextBeansAsAttributes(true);
		return viewResolver;
	}

	/**
	 * 重写WebMvcConfigurer中配置方法，添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor())
				.addPathPatterns("/go")
				.excludePathPatterns("/resources/**");
	}
	
}
