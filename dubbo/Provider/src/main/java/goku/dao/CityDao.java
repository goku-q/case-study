package goku.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import goku.pojo.City;

public interface CityDao {
	@Insert("insert into city (name,userId) value(#{name},#{userId})")
	public void saveUserCity(@Param("name") String name,@Param("userId") Integer userId);
	@Select("select * from city where name=#{name} and userId=#{userId}")
	public City findeByCityAndId(@Param("name") String name,@Param("userId") Integer userId);
}
