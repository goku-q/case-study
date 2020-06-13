package spring_mybatis.spring_mybatis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import spring_mybatis.spring_mybatis.config.AnnotationConfig;
import spring_mybatis.spring_mybatis.pojo.Bank;

/**
 * 基于纯注解实现spring整合mybatis
 * @author tmac-q
 *
 */
@Service
public class BankServiceByAnnotation {

	@Autowired
	private BankDao bankDao;
	
	public Bank getUserByAnnotation(int id) {
		return bankDao.getUserByAnnotation(id);
	};
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfig.class);
		BankServiceByAnnotation bankservie = context.getBean(BankServiceByAnnotation.class);
		Bank bank = bankservie.getUserByAnnotation(1);
		System.out.println(bank.getName());
	}
}
