package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BankController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
