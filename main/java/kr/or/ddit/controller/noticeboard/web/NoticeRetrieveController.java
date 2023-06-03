package kr.or.ddit.controller.noticeboard.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.controller.noticeboard.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice")
public class NoticeRetrieveController {

	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value = "/list.do")
	public String noticeList() {
		log.info("noticeList() 실행...!");
		return "notice/list";
	}
	
	@RequestMapping(value = "/detail.do")
	public String noticeDetail(int boNo, Model model) {
		NoticeVO notice = noticeService.selectNotice(boNo);
		model.addAttribute("notice", notice);
		return "notice/detail";
	}
}
