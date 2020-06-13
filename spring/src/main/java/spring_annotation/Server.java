package spring_annotation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class Server {
	public Server() {
		super();
		System.out.println("执行server构造方法");
	}

	@Autowired
	private Dao dao;
	
	public void test() {
		dao.run();
	}
}
