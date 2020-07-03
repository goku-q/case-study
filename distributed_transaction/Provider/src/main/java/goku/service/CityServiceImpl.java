package goku.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import goku.dao.CityDao;
import goku.dao.TaskHisDao;
import goku.pojo.City;
import goku.pojo.TaskHis;
import goku.task.RabbitMQConfig;
@Service
@Transactional
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityDao cityDao;
	@Autowired
	private TaskHisDao taskHisDao;
	public boolean saveUserCity(String name,Integer userId,String taskId) {
		//保证幂等性，先查询在添加
		City city = cityDao.findeByCityAndId(name, userId);
		if (city!=null) {
			return false;
		}
		cityDao.saveUserCity(name, userId);
		//保证幂等性，先查询在添加
		TaskHis task = taskHisDao.findById(taskId);
		if(task!=null) {
			return false;
		}
		TaskHis taskHis = new TaskHis();
		taskHis.setId(taskId);//模拟，id不做处理
		taskHis.setCreateTime(new Date());
		taskHis.setDeleteTime(null);
		taskHis.setUpdateTime(new Date());
		taskHis.setErrormsg(null);
		taskHis.setRequestBody("{\"id\":\""+taskId+"\",\"city\":\""+name+"\",\"userId\":\""+userId+"\"}");
		taskHis.setStatus("finished");
		taskHis.setVersion(1);
		taskHis.setTaskType("add");
		taskHis.setMqExchange(RabbitMQConfig.EX_CHANGE);
		taskHis.setMqRoutingkey(RabbitMQConfig.ADD_ROUTING_KEY);
		taskHisDao.saveTaskHis(taskHis);
		return true;
	}
}
