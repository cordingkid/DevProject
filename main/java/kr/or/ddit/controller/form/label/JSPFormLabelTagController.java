package kr.or.ddit.controller.form.label;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/formtag/label")
@Slf4j
public class JSPFormLabelTagController {
	/*
	 	13. 라벨 요소
	 */
	
	@GetMapping("/registerFormLabel01")
	public String registerFormLabel01(Model model) {
		log.info("registerFormLabel01() 실행...!");
		model.addAttribute("member",new Member());
		return "form/label/registerFormLabel01";
	}
}
