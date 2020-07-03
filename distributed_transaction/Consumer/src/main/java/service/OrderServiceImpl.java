package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pojo.User;
@Component
public class OrderServiceImpl implements OrderService {
	@Autowired
	private UserService userService;

	public void showUser() {
		System.out.println("开始远程调用");
		User user = userService.getUser();
		System.out.println("用户姓名"+user.getNameS()+"--------------地址："+user.getAddr());
	}

}
