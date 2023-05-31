package kr.or.ddit.controller.form.radio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.CodeLabelValue;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/formtag/radio")
@Slf4j
public class JSPFormRadioTagController {
	/*
	 	8장 스프림 폼 태그
	 		
	 		9. 여거 개의 라디오 버튼 요소
	 		 - HTML 라이도 버튼을 출력하려면 form:radiobuttons 사용
	 */
	
	@GetMapping("/registerFormRadios01")
	public String registerFormRadios01(Model model) {
		log.info("registerFormRadios01() 실행....!!");
		Map<String, String> genderCodeMap = new HashMap<String, String>();
		genderCodeMap.putIfAbsent("01", "Male");
		genderCodeMap.putIfAbsent("02", "FeMale");
		genderCodeMap.putIfAbsent("03", "Other");
		
		model.addAttribute("member",new Member());
		model.addAttribute("genderCodeMap", genderCodeMap);
		return "form/radio/registerFormRadios01";
	}
	
	@GetMapping("/registerFormRadios02")
	public String registerFormRadios02(Model model) {
		log.info("registerFormRadios02() 실행....!!");
		
		List<CodeLabelValue> genderCodeList = new ArrayList<CodeLabelValue>();
		
		genderCodeList.add(new CodeLabelValue("01", "Male"));
		genderCodeList.add(new CodeLabelValue("02", "FeMale"));
		genderCodeList.add(new CodeLabelValue("03", "Other"));
		
		model.addAttribute("member",new Member());
		model.addAttribute("genderCodeList", genderCodeList);
		return "form/radio/registerFormRadios02";
	}
	
	@PostMapping("/result")
	public String registerFormRadioResult(Model model,Member member) {
		log.info("registerFormRadioResult() 실행....!!");
		log.info("member.getGender() : " + member.getGender());
		model.addAttribute("gender", member.getGender());
		return "form/radio/result";
	}
	
	
	// 여기서 부터는 단일 radio 버튼입니다.
	
	/*
	 	10. 라이도 버튼 요소
	 	 - HTML 라디오 버튼을 출력 하려면 form:radiobutton 요소를 사용한다.
	 */
	
	@GetMapping("/registerFormRadio01")
	public String registerFormRadio01(Model model) {
		log.info("registerFormRadio01() 실행...!!!");
		model.addAttribute("member", new Member());
		return "form/radio/registerFormRadio01";
	}
	
	@GetMapping("/registerFormRadio02")
	public String registerFormRadio02(Model model) {
		log.info("registerFormRadio02() 실행...!!!");
		Member member = new Member();
		member.setGender("male");
		model.addAttribute("member", member);
		return "form/radio/registerFormRadio01";
	}
}































