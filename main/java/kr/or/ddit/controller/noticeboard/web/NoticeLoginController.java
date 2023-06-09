package kr.or.ddit.controller.noticeboard.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.service.INoticeService;
import kr.or.ddit.vo.DDITMemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeLoginController {

	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String noticeLogin(Model model) {
		model.addAttribute("bodyText", "login-page");
		return "conn/login";
	}
	
	@RequestMapping(value = "/signup.do", method = RequestMethod.GET)
	public String noticeRegister(Model model) {
		model.addAttribute("bodyText", "register-page");
		return "conn/register";
	}
	
	@ResponseBody
	@RequestMapping(value = "/idCheck.do", method = RequestMethod.POST)
	public ResponseEntity<ServiceResult> idCheck(String memId) {
		ServiceResult res = noticeService.idCheck(memId);
		return new ResponseEntity<ServiceResult>(res,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/signup.do", method = RequestMethod.POST)
	public String signup(
			HttpServletRequest req,
			DDITMemberVO memberVO, Model model,
			RedirectAttributes ra) {
		String goPage = "";
		
		Map<String, String> errors = new HashMap<String, String>();
		if (StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디를 입력해 주세요.");
		}
		if (StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "아이디를 입력해 주세요.");
		}
		if (StringUtils.isBlank(memberVO.getMemName())) {
			errors.put("memName", "아이디를 입력해 주세요.");
		}
		
		if (errors.size() > 0) {
			model.addAttribute("bodyText", "register-page");
			model.addAttribute("errors", errors);
			model.addAttribute("member", memberVO);
			goPage = "conn/register";
		}else {
			ServiceResult res = noticeService.signup(req,memberVO);
			if(res.equals(ServiceResult.OK)) {
				ra.addFlashAttribute("message", "정상적으로 회원가입이 완료되었습니다.");
				goPage = "redirect:/notice/login.do";
			}else {
				model.addAttribute("bodyText", "register-page");
				model.addAttribute("message", "서버에러, 다시 시도해주세요.");
				model.addAttribute("member", memberVO);
				goPage = "conn/register";
			}
		}
		return goPage;
	}
	
	@RequestMapping(value = "/forget.do", method = RequestMethod.GET)
	public String noticeForget(Model model) {
		model.addAttribute("bodyText", "login-page");
		return "conn/forget";
	}
	
	@RequestMapping(value = "/loginCheck.do", method = RequestMethod.POST)
	public String loginCheck(
			HttpServletRequest req,
			DDITMemberVO memberVO, Model model) {
		String goPage = "";
		
		Map<String, String> errors = new HashMap<String, String>();
		if (StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요.");
		}
		if (StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요.");
		}
		
		if (errors.size() > 0) {
			model.addAttribute("bodyText", "login-page");
			model.addAttribute("errors", errors);
			model.addAttribute("member", memberVO);
			goPage = "conn/login";
		}else {
			DDITMemberVO member = noticeService.loginCheck(memberVO);
			if(member != null) {
				HttpSession session = req.getSession();
				session.setAttribute("SessionInfo", member);
				goPage = "redirect:/notice/list.do";
			}else {
				model.addAttribute("bodyText", "login-page");
				model.addAttribute("message", "서버에러, 로그인 정보를 정확하게 입력해주세요.");
				model.addAttribute("member", memberVO);
				goPage = "conn/login";
			}
		}
		return goPage;
	}
	
	@ResponseBody
	@RequestMapping(value = "/idForget.do", method = RequestMethod.POST)
	public ResponseEntity<String> idForgetProcess(@RequestBody DDITMemberVO member){
		log.info("맴버 : " + member);
		String memId = noticeService.idForgetProcess(member);
		return new ResponseEntity<String>(memId, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/pwForget.do", method = RequestMethod.POST)
	public ResponseEntity<String> pwForgetProcess(@RequestBody DDITMemberVO member){
		String pwd = noticeService.pwForgetProcess(member);
		return new ResponseEntity<String>(pwd,HttpStatus.OK);
	}
	
}































