package spring_transaction;



import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * 模拟连接数据库
 * @author tmac-q
 *
 */
public class BankDao extends JdbcDaoSupport{
		
	public void save() throws SQLException {
		Statement statement = this.getConnection().createStatement();
		boolean result = statement.execute("insert into test values(1,'lisi')");
		System.out.println("数据存入成功");
	}
	public void del() throws SQLException {
		Statement statement = this.getConnection().createStatement();
		boolean result = statement.execute("delete from test ");
		System.out.println("数据删除成功");
	}
	public void update() throws SQLException {
		Statement statement = this.getConnection().createStatement();
		boolean result = statement.execute("update test set name ='goku' where id=1");
		System.out.println("更新数据成功");
	}
}
