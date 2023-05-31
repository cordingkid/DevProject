package kr.or.ddit.controller.form.checkboxes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.CodeLabelValue;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/formtag/checkboxes")
@Slf4j
public class JSPFormCheckboxesTagController<V> {
	/*
	 	8장 스프링 폼 태그
	 	
	 		7. 여러 개의 체크박스 요소
	 		 - HTML 여러개의 체크박스를 출력하려면form:chckboxes 요소를사용
	 */
	
	@GetMapping("/registerForm01")
	public String registerForm01(Model model) {
		log.info("registerForm01() 실행...!!");
		
		Map<String,Object> hobbyMap = new HashMap<String, Object>();
		hobbyMap.put("01", "Drive");
		hobbyMap.put("02", "Music");
		hobbyMap.put("03", "Movie");
		model.addAttribute("hobbyMap", hobbyMap);
		model.addAttribute("member", new Member());
		return "form/checkboxes/registerForm01";
	}
	
	@GetMapping("/registerForm02")
	public String registerForm02(Model model) {
		log.info("registerForm02() 실행...!!");
		
		List<CodeLabelValue> hoCodeList = new ArrayList<>();
		hoCodeList.add(new CodeLabelValue("01", "Drive"));
		hoCodeList.add(new CodeLabelValue("02", "Music"));
		hoCodeList.add(new CodeLabelValue("03", "Movie"));
		
		model.addAttribute("hoCodeList", hoCodeList);
		model.addAttribute("member", new Member());
		return "form/checkboxes/registerForm02";
	}
}
























