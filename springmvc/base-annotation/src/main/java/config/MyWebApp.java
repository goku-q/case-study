package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
/**
 * 相当于web.xml，利用的是SPI机制，
 * 	SPI机制：
	提供方在‘META-INF/services’目录下新建一个名称为接口全限定名的文件，内容为接口实现类的全限定名。
	调用方通过ServiceLoader.load方法加载接口的实现类实例
 * @author tmac-q
 *
 */
public class MyWebApp implements WebApplicationInitializer {

	@Override
	/**
	 * ServletContext 参数可以配置web.xml中的各种标签
	 */
	public void onStartup(ServletContext servletContext) throws ServletException {
		//加载配置类信息
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(MyConfig.class);
		//创建dispatcherservlet对象
		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
		//将dispatcherservlet配置到servletContext中,和web.xml中一样
		Dynamic dynamic = servletContext.addServlet("dispatchter", dispatcherServlet);
		dynamic.addMapping("/");
		dynamic.setLoadOnStartup(1);
	}

}
