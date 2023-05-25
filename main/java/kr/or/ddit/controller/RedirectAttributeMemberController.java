package kr.or.ddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/redirectattribute")
@Slf4j
public class RedirectAttributeMemberController {

	/*
	 * 4. RedirectAttribute 타입
	 *  - redirectAttribute는 일회성으로 데이터를 전달하는 용도로 사용한다.
	 */
	
	@GetMapping(value = "/registerForm")
	public String registerForm() {
		return "member/registerRedirectAttributeFrom";
	}
	
	@PostMapping("/register")
	public String register(Member member, RedirectAttributes redirectAttributes) {
		log.info("register() 실행...!");
		redirectAttributes.addFlashAttribute("msg","success");
		return "redirect:/redirectattribute/result";
	}
	
	@GetMapping("/result")
	public String result() {
		log.info("result() 실행...!");
		return "result";
	}
	
}






















