package spring_xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
		Dao dao = applicationContext.getBean(Dao.class);
		Service service = (Service)applicationContext.getBean("service");
		Controller controller = (Controller) applicationContext.getBean("controller");
		System.out.println(controller.map.get("goku"));
	}
}
