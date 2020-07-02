package goku.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
	@Insert("insert into tb_user (id,name) value(#{id},#{name})")
	public void saveUser(@Param("id") Integer id,@Param("name")String name);
}
