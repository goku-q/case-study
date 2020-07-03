package service;

import pojo.User;

public class UserServiceImpl implements UserService {

	public User getUser() {
		User user = new User();
		user.setId(1);
		user.setNameS("goku");
		user.setAddr("火星");
		return user;
	}

}
