package spring_mybatis.spring_mybatis.dao;

import org.apache.ibatis.annotations.Select;

import spring_mybatis.spring_mybatis.pojo.Bank;

public interface BankDao {
	public Bank getUser(int id);
	@Select("select * from test where id = #{id}")//基于注解实现
	public Bank getUserByAnnotation(int id);
	public void save(Bank bank);
}
