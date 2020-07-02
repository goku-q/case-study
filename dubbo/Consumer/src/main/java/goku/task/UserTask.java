package goku.task;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import goku.dao.TaskDao;
import goku.pojo.Task;
@Component
public class UserTask {

	@Autowired
	private TaskDao TaskDao;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Scheduled(fixedRate = 10000)
	public void checkUserTask() {
		System.out.println("定时任务开始执行 rate 10second");
		List<Task> tasks = TaskDao.findTasks();
		for(Task task:tasks) {
			rabbitTemplate.convertAndSend(task.getMqExchange(), task.getMqRoutingkey(), task.getRequestBody());
			System.out.println("定时任务发送 task MQ");
		}
		
	}
}
