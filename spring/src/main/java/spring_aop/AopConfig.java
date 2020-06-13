package spring_aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 注解配置类
 * spring aop是基于jdk的动态代理、及cglib代理。跟aspectj的关系是，只是支持使用aspectj的语法格式
 * @author tmac-q
 *
 */
@Configuration//声明为配置类
@ComponentScan//开启注解扫描
@EnableAspectJAutoProxy//开启aspectj语法注解支持，不起开无法使用aop注解
public class AopConfig {

}
