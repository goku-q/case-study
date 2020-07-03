package goku.service;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goku.dao.TaskDao;
import goku.dao.TaskHisDao;
import goku.dao.UserDao;
import goku.pojo.Task;
import goku.pojo.TaskHis;
import goku.task.RabbitMQConfig;

@Service
@Transactional//统一处理本地事务
public class UserServiceImpl {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private TaskServiceImpl taskService;
	@Autowired
	private TaskHisDao taskHisDao;
	/**
	 * 添加用户和保存MQ task任务，统一本地事务处理
	 * @param id
	 * @param name
	 */
	public void saveUser(Integer id,String name) {
		//保存user用户
		userDao.saveUser(id, name);
		//开始保存本地任务Task，创建Task对象
		Task task = new Task();
		task.setId(id+name);//模拟，id不做处理
		task.setCreateTime(new Date());
		task.setDeleteTime(null);
		task.setUpdateTime(new Date());
		task.setErrormsg(null);
		task.setRequestBody("{\"id\":\""+task.getId()+"\",\"city\":\"beijing\",\"userId\":\""+id+"\"}");
		task.setStatus("add");
		task.setVersion(1);
		task.setTaskType("add");
		task.setMqExchange(RabbitMQConfig.EX_CHANGE);
		task.setMqRoutingkey(RabbitMQConfig.ADD_ROUTING_KEY);
		taskService.insertTask(task);
		//***********************以下发送MQ*************************
		System.out.println("发送MQ...");
		rabbitTemplate.convertAndSend(RabbitMQConfig.EX_CHANGE,RabbitMQConfig.ADD_ROUTING_KEY, task.getRequestBody());
		System.out.println("MQ发送成功...");
	}
	
	public boolean taskAndHis(String id) {
		//幂等性验证
		TaskHis taskHis2 = taskHisDao.findById(id);
		if (taskHis2!=null) {
			return false;
		}
		Task task = taskService.findById(id);
		if (task!=null) {
			taskService.delete(id);
		}
		TaskHis taskHis = new TaskHis();
		taskHis.setId(task.getId());//模拟，id不做处理
		taskHis.setCreateTime(new Date());
		taskHis.setDeleteTime(null);
		taskHis.setUpdateTime(new Date());
		taskHis.setErrormsg(null);
		taskHis.setRequestBody(task.getRequestBody());
		taskHis.setStatus(task.getStatus());
		taskHis.setVersion(1);
		taskHis.setTaskType(task.getTaskType());
		taskHis.setMqExchange(task.getMqExchange());
		taskHis.setMqRoutingkey(task.getMqRoutingkey());
		taskHisDao.saveTaskHis(taskHis);
		return true;
	}
}
