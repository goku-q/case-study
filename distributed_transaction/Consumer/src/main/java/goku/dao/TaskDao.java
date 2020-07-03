package goku.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import goku.pojo.Task;

public interface TaskDao {
	@Insert("insert into task (id,create_time,update_time,delete_time,task_type,mq_exchange,mq_routingkey,request_body,version,status,errormsg) "
			+ "value(#{id},#{createTime},#{deleteTime},#{updateTime},#{taskType},#{mqExchange},#{mqRoutingkey},#{requestBody},#{version},#{status},#{errormsg})")
	public void insertTask(Task task);

	@Select("select * from task")
	@Results({@Result(property = "createTime", column = "create_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "requestBody", column = "request_body"),
			@Result(property = "deleteTime", column = "delete_time"),
			@Result(property = "taskType", column = "task_type"),
			@Result(property = "mqExchange", column = "mq_exchange"),
			@Result(property = "mqRoutingkey", column = "mq_routingkey"),
			@Result(property = "version", column = "version"),
			@Result(property = "status", column = "status"),
			@Result(property = "errormsg", column = "errormsg") })
	public List<Task> findTasks();
	@Delete("delete from task where id=#{id}")
	public boolean delById(String id);
	@Select("select * from task where id =#{id}")
	public Task findById(String id);
}
