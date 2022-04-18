package kr.spring.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/main/main.do")
	public String main() {
		System.out.println("실행");
		//타일스 설정(main.xml)
		return "main"; // main.jsp(X)
	}
	
}