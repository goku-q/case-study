package spring_transaction.annotation;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模拟业务层
 * @author tmac-q
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED) //整个类都参与事务
public class BankService {

	@Autowired
	private BankDao banDao;
	@Transactional(propagation = Propagation.REQUIRED)  //以局部事务为准
	public void save() throws SQLException {
		banDao.save();
	}
	public void del() throws SQLException {
		banDao.del();
	}
	public void update() throws SQLException {
		banDao.update();
		System.out.println("数据异常，回滚");
		int i = 1/0;//主动制造一个异常
	}
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BankConfig.class);
		BankService bankService = context.getBean(BankService.class);
		bankService.save();
		//bankService.del();
		bankService.update();
		
	}
}
