package spring_mybatis.spring_mybatis.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("test")//不加注释默认为类小写，bank
public class Bank implements Serializable{

	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
