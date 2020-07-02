package goku.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import goku.pojo.TaskHis;

public interface TaskHisDao {

	@Insert("insert into task_his (id,create_time,update_time,delete_time,task_type,mq_exchange,mq_routingkey,request_body,version,status,errormsg) "
			+ "value(#{id},#{createTime},#{deleteTime},#{updateTime},#{taskType},#{mqExchange},#{mqRoutingkey},#{requestBody},#{version},#{status},#{errormsg})")
	public void saveTaskHis(TaskHis taskHis);
	@Select("select * from task_his where id = #{id}")
	public TaskHis findById(String id);
}
