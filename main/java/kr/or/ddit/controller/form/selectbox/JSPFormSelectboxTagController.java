package kr.or.ddit.controller.form.selectbox;

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
@RequestMapping("/formtag/selectbox")
@Slf4j
public class JSPFormSelectboxTagController {
	/*
	 * 11. 셀렉트 박스요소
	 */
	
	@GetMapping("/registerFormSelectbox01")
	public String registerFormSelectbox01(Model model) {
		log.info("registerFormSelectbox01() 실행...!");
		
		Map<String, String> nationalityCodeMap = new HashMap<String, String>();
		
		nationalityCodeMap.put("01", "Korea");
		nationalityCodeMap.put("02", "Canada");
		nationalityCodeMap.put("03", "USA");
		
		model.addAttribute("member", new Member());
		model.addAttribute("nationalityCodeMap", nationalityCodeMap);
		
		return "form/selectbox/registerFormSelectbox01";
	}
	
	
	@GetMapping("/registerFormSelectbox02")
	public String registerFormSelectbox02(Model model) {
		log.info("registerFormSelectbox02() 실행...!");
		
		List<CodeLabelValue> nationalityCodeList = new ArrayList<>();
		
		nationalityCodeList.add(new CodeLabelValue("01", "Korea"));
		nationalityCodeList.add(new CodeLabelValue("02", "Canada"));
		nationalityCodeList.add(new CodeLabelValue("03", "USA"));
		
		
		model.addAttribute("member", new Member());
		model.addAttribute("nationalityCodeList", nationalityCodeList);
		
		return "form/selectbox/registerFormSelectbox02";
	}
	
	@PostMapping("/result")
	public String registerFormSelectboxResult(Model model, Member member) {
		log.info("registerFormSelectboxResult() 실행...!");
		log.info("member.getNationality() : " + member.getNationality());
		model.addAttribute("member", member);
		return "form/selectbox/result";
	}
	
	@GetMapping("/registerFormSelectbox03")
	public String registerFormSelectbox03(Model model) {
		log.info("registerFormSelectbox03() 실행..!!!!!!!!!!!!!!!");
		List<CodeLabelValue> carCodeList = new ArrayList<CodeLabelValue>();
		carCodeList.add(new CodeLabelValue("jeep","Jeep"));
		carCodeList.add(new CodeLabelValue("audi","Audi"));
		carCodeList.add(new CodeLabelValue("volvo","Volvo"));
		carCodeList.add(new CodeLabelValue("genesis","Genesis"));
		model.addAttribute("carCodeList", carCodeList);
		model.addAttribute("member", new Member());
		return "form/selectbox/registerFormSelectbox03";
	}
}













