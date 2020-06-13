package spring_mybatis.spring_mybatis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import spring_mybatis.spring_mybatis.pojo.Bank;

@Service
public class BankService {
	@Autowired
	private BankDao bankDao;
	
	public Bank getUser(int id) {
		return bankDao.getUser(id);
	};
	public void save(Bank bank) {
		bankDao.save(bank);
	};
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_mybatis/spring_mybatis/config/application-mybatis.xml");
		BankService bankService = context.getBean(BankService.class);
		Bank bank = (Bank)bankService.getUser(1);
		System.out.println(bank.getName());
	}
}
