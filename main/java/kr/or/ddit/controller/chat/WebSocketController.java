package kr.or.ddit.controller.chat;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/websocket")
public class WebSocketController {

	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public String websocketTestLogin() {
		return "chat/loginChat";
	}
	
	@RequestMapping(value = "/test1.do", method = RequestMethod.GET)
	public String websocketTest(String name, HttpSession session, Model model) {
		String page = "";
		log.info("이름 : " + name);
		if (name == null || name.trim().equals("")) {
			model.addAttribute("msg", "이름을 입력해 주세요.");
			page = "chat/loginChat";
		}else {
			session.setAttribute("name", name);
			page = "chat/chatTest";
		}
		return page;
	}
}
