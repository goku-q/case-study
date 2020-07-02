package goku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goku.dao.TaskDao;
import goku.pojo.Task;
/**
 * 操作Task的业务处理
 * @author tmac-q
 *
 */
@Service
@Transactional
public class TaskServiceImpl {
	@Autowired
	private TaskDao taskDao;
	
	public void insertTask(Task task) {
		taskDao.insertTask(task);
	}
	public void delete(String id) {
		taskDao.delById(id);
	}
	public Task findById(String id) {
		return taskDao.findById(id);
	}
}
