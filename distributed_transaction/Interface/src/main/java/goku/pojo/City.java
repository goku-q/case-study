package goku.pojo;

import java.io.Serializable;

/**
 * 需要实现可序列化
 * @author tmac-q
 *
 */
public class City implements Serializable{
	private String name;
	private Integer userId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
