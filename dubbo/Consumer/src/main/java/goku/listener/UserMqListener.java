package goku.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import goku.service.UserServiceImpl;
@Component
public class UserMqListener implements MessageListener{
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	public void onMessage(Message message) {
		byte[] body = message.getBody();
		String result = new String(body);
		System.out.println("*************************************");
		System.out.println(result);
		boolean flag = userServiceImpl.taskAndHis(result);
	}

}
