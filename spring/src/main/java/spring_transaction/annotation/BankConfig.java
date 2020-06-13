package spring_transaction.annotation;


import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 注解实现事务管理，配置类
 * @author tmac-q
 *
 */
@Configuration  //声明为配置类
@ComponentScan  //扫描包
@EnableTransactionManagement //开启事务
public class BankConfig {

	//将数据源注入容器（dbcp）
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setUrl("jdbc:mysql://192.168.0.9:3306/test");
		dataSource.setPassword("123321");
		return dataSource;
	}
	
	//将事务管理类注入容器
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}
	//将将jdbcTemplate注入容器
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}
	
}
