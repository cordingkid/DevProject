package kr.or.ddit.controller.form.checkbox;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/formtag/checkbox")
public class JSPFormCheckboxTagController {
	/*
	 	8장 스프림 폼 태그
	 	
	 		8. 체크박스 요소
	 		 - HTML 체크박스를 출력하려면 form:checkbox 사용
	 */
	
	// 1) 모델에 기본 생성자로 생성한 폼 객체를 추가한 후에 화면에 전달
	@GetMapping("/registerForm01")
	public String registerForm01(Model model) {
		log.info("registerForm01() 실행...!!!!");
		model.addAttribute("member", new Member());
		return "form/checkbox/registerForm01";
	}
	
	
	@GetMapping("/registerForm02")
	public String registerForm02(Model model) {
		log.info("registerForm02() 실행...!!!!");
		
		Member member = new Member();
		member.setDeveloper("Y");
		member.setForeginer(true);
		member.setHobby("dirve");
		
		String[] hrr = {"drive","music"};
		member.setHobbyArray(hrr);
		
		List<String> hList = new ArrayList<String>();
		hList.add("music");
		member.setHobbyList(hList);
		
		model.addAttribute("member", member);
		return "form/checkbox/registerForm01";
	}
	
	@PostMapping("/result")
	public String registerResult(Member member, Model model) {
		log.info("registerResult() 실행....!");
		log.info("member.getDeveloper() : "+ member.getDeveloper());
		log.info("member.isForeginer() : "+ member.isForeginer());
		log.info("member.getHobby() : "+ member.getHobby());
		
		String[] hobbyArray = member.getHobbyArray();
		if(hobbyArray != null) {
			for (String str : hobbyArray) {
				log.info("hobbyArray : " + str);
			}
		}else {
			log.info("하비어레이 이즈 널");
		}
		
		List<String> hobbyList = member.getHobbyList();
		if(hobbyList != null) {
			for (String str : hobbyList) {
				log.info("hobbyList : " + str);
			}
		}else {
			log.info("하비리스트 이즈 널");
		}
		 
		model.addAttribute("member", member);
		return "form/checkbox/result";
	}
}












