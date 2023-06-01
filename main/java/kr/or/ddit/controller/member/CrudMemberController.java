package kr.or.ddit.controller.member;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.IMemberService;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import oracle.net.aso.l;

@Controller
@Slf4j
@RequestMapping("/crud/member")
public class CrudMemberController {

	@Inject
	private IMemberService service;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String crudMemberRegisterForm(Model model) {
		log.info("crudMemberRegisterForm() 실행...!");
		return "crud/member/register";
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String crudMemberRegister(MemberVO member, Model model) {
		log.info("crudMemberRegister() 실행...!");
		service.register(member);
		model.addAttribute("msg", "등록이 완료됬습니다.");
		return "crud/member/success";
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Model model) {
		List<MemberVO> memberList = service.list();
		model.addAttribute("memberList", memberList);
		return "/crud/member/list";
	}
	
	@RequestMapping(value = "/read",method = RequestMethod.GET)
	public String read(int userNo,Model model) {
		MemberVO member = service.read(userNo);
		model.addAttribute("member", member);
		return "crud/member/read";
	}
}























