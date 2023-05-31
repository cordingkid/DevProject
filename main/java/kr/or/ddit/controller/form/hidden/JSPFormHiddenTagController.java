package kr.or.ddit.controller.form.hidden;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/formtag/hidden")
@Slf4j
public class JSPFormHiddenTagController {
	/*
	 *	12. 숨겨진 필드 요소 
	 *	 - form:hidden을 사용해라
	 */
	
	@GetMapping("/registerFormHidden01")
	public String registerFormHidden01(Model model) {
		log.info("registerFormHidden01() 실행...!");
		
		Member member = new Member();
		member.setUserName("재균");
		model.addAttribute("member", member);
		return "form/hidden/registerFormHidden01";
	}
	
	@PostMapping("/result")
	public String registerFormHiddenResult(Model model, Member member) {
		log.info("registerFormHiddenResult() 실행...!");
		log.info("member.getUserId() : " + member.getUserId());
		log.info("member.getUserName() : " + member.getUserName());
		return "form/hidden/result";
	}
}














