package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BankController {

	@RequestMapping("/hello")
	public String hello() {
		System.out.println("hello");
		return "hello";
	}
	
	@RequestMapping("/go")
	public String go() {
		return "go";
	}
	
	@RequestMapping("/bye")
	public String bye() {
		return "bye";
	}
}
