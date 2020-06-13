package spring_xml;

public class Dao {

	public Dao() {
		super();
		System.out.println("执行Dao构造方法！");
	}
	String name;
	String age;
	public Dao(String name, String age) {
		super();
		System.out.println("执行Dao构造方法！");
		this.name = name;
		this.age = age;
		System.out.println(name+"\t"+age);
	}
}
