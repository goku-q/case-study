package spring_xml;

import java.util.Map;

public class Controller {

	Map<String, String> map;
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Controller() {
		super();
		System.out.println("执行controller构造方法！");
	}

}
