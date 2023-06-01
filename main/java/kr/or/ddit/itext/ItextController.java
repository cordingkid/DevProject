package kr.or.ddit.itext;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.TestUser;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/itext")
@Slf4j
public class ItextController {

	@GetMapping("/test01")
	public String itextText01() {
		return "itext/test01";
	}
	
	@PostMapping("/test01")
	public String login(TestUser user, HttpSession session) {
		session.setAttribute("user", user);
		return "redirect:/itext/test02";
	}
	
	@RequestMapping(value = "/test02", method = RequestMethod.GET)
	public String test02() {
		return "itext/test02";
	}
	
//	@RequestMapping(value = "/download01", method = RequestMethod.POST)
//	public ResponseEntity<String> download01(){
//		
//	}
}
