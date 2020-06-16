package pojo;

import java.io.Serializable;

/**
 * 需要实现可序列化
 * @author tmac-q
 *
 */
public class User implements Serializable{
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNameS() {
		return nameS;
	}
	public void setNameS(String nameS) {
		this.nameS = nameS;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	private Integer id;
	private String nameS;
	private String addr;

}
