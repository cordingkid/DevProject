package kr.or.ddit.controller.noticeboard.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.service.INoticeService;
import kr.or.ddit.vo.DDITMemberVO;
import kr.or.ddit.vo.NoticeVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeInsertController {

	@Inject
	private INoticeService noticeService;

//	private TelegramSendController tst = new TelegramSendController();

	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String noticeInsertForm() {
		return "notice/form";
	}

	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String noticeInsert(NoticeVO noticeVO, Model model,
			HttpServletRequest req, RedirectAttributes ra) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();

		if (StringUtils.isBlank(noticeVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세여.");
		}

		if (StringUtils.isBlank(noticeVO.getBoContent())) {
			errors.put("boTitle", "제목을 입력해주세여.");
		}

		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			model.addAttribute("noticeVO", noticeVO);
			goPage = "notice/form";
		} else {
			// 시큐리티 적용후 시큐리티에 있는 principal 로꺼냄
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			noticeVO.setBoWriter(user.getUsername());
			ServiceResult res = noticeService.insertNotice(req, noticeVO);
			if (res.equals(ServiceResult.OK)) {
				goPage = "redirect:/notice/detail.do?boNo=" + noticeVO.getBoNo();
			} else {
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				goPage = "notice/form";
			}
		}

		return goPage;
	}
	
	// 요소URI : /notice/generalForm
	// 요청 방식 : get
	@GetMapping("/generalForm")
	public String generalForm() {
		return "notice/generalForm";
	}
	
	/** ResponseBody json, 이긴한데 텍스트로 리턴해줌 */
//	@ResponseBody
//	@PostMapping("/generalFormPost")
//	public String generalFormPost(NoticeVO noticeVO) {
//		log.info("noticeVO" + noticeVO);
//		
//		String uploadFolder = "D:\\A_TeachingMaterial\\6_jspSpring\\02.SPRING2\\workspace_spring2\\DevProject\\src\\main\\webapp\\resources\\upload";
//		
//		MultipartFile[] boFile = noticeVO.getBoFile();
//		
//		for (MultipartFile file : boFile) {
//			File saveFile = new File(uploadFolder, file.getOriginalFilename());
//			
//			// 파일 복사 실행
//			//( 파일원본.transferTo(설계))
//			try {
//				file.transferTo(saveFile);
//				return "Success";
//			} catch (IllegalStateException | IOException e) {
//				log.error(e.getMessage());
//				return "Failed";
//			}
//		}
//		
//		ServiceResult res = noticeService.insertNotice(noticeVO);
//		
//		
//		return "Success";
//	}

}























