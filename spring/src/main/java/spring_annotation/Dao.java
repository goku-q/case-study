package spring_annotation;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component 组件，适用于定义不清晰的类，放入容器
@Repository //与@Component一样，专用于dao层
@Scope("singleton")//单例模式
@Lazy//延迟加载
public class Dao {

	public Dao() {
		super();
		System.out.println("执行Dao构造方法！");
	}
	
	public void run() {
		System.out.println("连接dao层");
	}
}
