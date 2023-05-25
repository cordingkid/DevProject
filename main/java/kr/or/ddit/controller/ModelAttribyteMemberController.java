package kr.or.ddit.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Member;

@Controller
@Slf4j
@RequestMapping("/modelattribute")
public class ModelAttribyteMemberController {
	
	// 3. @ModelAttribute 어노테이션
	//  - 매개변수로 전달받은 데이터를 전달할때 사용한다.
	
	@RequestMapping(value = "/registerForm", method = RequestMethod.GET)
	public String registerForm() {
		log.info("registerForm() 실행....!");
		return "member/registerModelAttributeForm";
	}
	
	@RequestMapping(value = "/register01", method = RequestMethod.POST)
	public String register01(String userId, String password) {
		log.info("register01() 실행....!");
		log.info("userId : " + userId);
		return "result";
	}
	
	@RequestMapping(value = "/register02", method = RequestMethod.POST)
	public String register02(
			@ModelAttribute("userId") String userId, 
			@ModelAttribute("password") String password) {
		log.info("register02() 실행....!");
		log.info("userId : " + userId);
		return "result";
	}
	
	@RequestMapping(value = "/register03", method = RequestMethod.POST)
	public String register03(Member member) {
		log.info("register03() 실행....!");
		member.setPassword("1111111111");
		log.info("userId : " + member.getUserId());
		log.info("password : " + member.getPassword());
		return "result";
	}
}
