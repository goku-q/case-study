package spring_transaction.annotation;



import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模拟连接数据库
 * @author tmac-q
 *
 */
@Repository
public class BankDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public void save() throws SQLException {
		jdbcTemplate.execute("insert into test values(1,'lisi')");
		System.out.println("数据存入成功");
	}
	public void del() throws SQLException {
		jdbcTemplate.execute("delete from test ");
		System.out.println("数据删除成功");
	}
	public void update() throws SQLException {
		jdbcTemplate.execute("update test set name ='goku' where id=1");
		System.out.println("更新数据成功");
	}
}
