package spring_xml;

import java.util.List;

public class Service {

	public Service() {
		super();
		System.out.println("执行service构造方法！");
	}

	List<String> list;

	public Service(List list) {
		super();
		System.out.println("执行service构造方法！");
		this.list = list;
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
}
