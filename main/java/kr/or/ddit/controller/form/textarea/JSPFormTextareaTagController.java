package kr.or.ddit.controller.form.textarea;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/formtag/textarea")
public class JSPFormTextareaTagController {
	/*
	 	8장. 스프링 폼 태그
	 	
	 		6. 텍스트에어리어
	 		 - HTML 텍스트 영역을 출력하려면 form:textarea 요소를 사용한다.
	 		 
	 */
	@GetMapping("/registerForm01")
	public String registerForm01(Model model) {
		log.info("registerForm01() 실행...!!");
		model.addAttribute("member", new Member());
		return "form/textarea/registerForm01";
	}
	
	@GetMapping("/registerForm02")
	public String registerForm02(Model model) {
		log.info("registerForm02() 실행...!!");
		Member member = new Member();
		member.setIntroduction("안녕\n 하세요 \n 개발원 \n언 제 끝 나 ?");
		model.addAttribute("member", member);
		return "form/textarea/registerForm01";
	}
}
