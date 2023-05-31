package kr.or.ddit.controller.form.validation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/formtag/validation")
public class JSPFormValidationController {
	
	@GetMapping("/registerFormValidation01")
	public String registerFormValidation01(Model model) {
		log.info("registerFormValidation01()  실행...!");
		model.addAttribute("member", new Member());
		return "form/validation/registerFormValidation01";
	}
	
	@PostMapping("/result")
	public String registerFormValidationResult(Member member) {
		log.info("registerFormValidationResult()  실행...!");
		log.info("member.getUserId() : " + member.getUserId());
		log.info("member.getUserName() : " + member.getUserName());
		log.info("member.getEmail() : " + member.getEmail());
		return "form/validation/result";
	}
}
