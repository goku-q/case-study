package spring_mybatis.mybatis;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import spring_mybatis.mybatis.pojo.Bank;
/**
 * mybatis事务默认不自动提交
 * @author tmac-q
 *
 */
public class BankDao {
	public static void main(String[] args) throws IOException {
		//读取mybatis配置进io流
		InputStream is = Resources.getResourceAsStream("spring_mybatis/mybatis/config/mybatis-config.xml");
		//创建sqlsessionfactory
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		//创建session连接
		SqlSession session = sessionFactory.openSession();
		//操作数据库方法一：
		//第一个参数为mapper.xml中的命令空间namespace+操作id，第二为参数
		Bank bank = (Bank)session.selectOne("spring_mybatis.mybatis.BankMapper.getUser", 1);
		System.out.println(bank.getName());
		//操作数据库方法二：
		//映射mapper接口方法，接口方法与mapper.xml中id保持一致
		BankMapper bankMapper = session.getMapper(BankMapper.class);
		Bank bank2 = new Bank();
		bank2.setId(2);
		bank2.setName("lisi");
		bankMapper.save(bank2);
		System.out.println(bankMapper.getUser(2).getName());
		session.commit();//事务默认为手动提交，可以在opensession中传参true改为自动提交
	}
}
