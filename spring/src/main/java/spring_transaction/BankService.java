package spring_transaction;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 模拟业务层
 * @author tmac-q
 *
 */
public class BankService {

	@Autowired
	private BankDao bankDao;
	
	public void save() throws SQLException {
		bankDao.save();
	}
	public void del() throws SQLException {
		bankDao.del();
	}
	public void update() throws SQLException {
		bankDao.update();
		System.out.println("数据异常，回滚");
		int i = 1/0;//主动制造一个异常
	}
	
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-tx.xml");
		BankService bankService = context.getBean(BankService.class);
		bankService.save();
		//bankService.del();
		bankService.update();
		
	}
}
