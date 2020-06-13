package spring_mybatis.spring_mybatis.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 基于纯注解实现spring整合mybatis
 * @author tmac-q
 *
 */
@Configuration//声明为配置文件
@ComponentScan("spring_mybatis.spring_mybatis") //容器扫描包配置
@MapperScan("spring_mybatis.spring_mybatis.dao")// 映射接口扫描，基于接口开发，不需要mapper.xml
public class AnnotationConfig {

	//配置数据源
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.0.9:3306/test");
		dataSource.setUsername("root");
		dataSource.setPassword("123321");
		return dataSource;
	}
	//注入sqlsessionfactorybean，交给spring管理
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		return sqlSessionFactoryBean;
	}
}
