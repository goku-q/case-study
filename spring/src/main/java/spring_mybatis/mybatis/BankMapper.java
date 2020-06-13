package spring_mybatis.mybatis;

import org.apache.ibatis.annotations.Insert;

import spring_mybatis.mybatis.pojo.Bank;
/**
 * 接口方法与mapper.xml中id保持一致
 * @author tmac-q
 *
 */
public interface BankMapper {

	public Bank getUser(int id);
	
	//也可以基于注解
	@Insert("insert into test values(#{id},#{name})")
	public void save(Bank bank);
}
