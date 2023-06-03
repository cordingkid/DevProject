package kr.or.ddit.controller.noticeboard.service;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.noticeboard.web.TelegramSendController;
import kr.or.ddit.mapper.NoticeMapper;
import kr.or.ddit.vo.NoticeVO;

@Service
public class NoticeServiceImpl implements INoticeService {

	@Inject
	private NoticeMapper noticeMapper;

	TelegramSendController tst = new TelegramSendController();

	@Override
	public ServiceResult insertNotice(NoticeVO noticeVO) {
		ServiceResult res = null;
		int status = noticeMapper.insertNotice(noticeVO);
		if (status > 0) {
			// 성공 했다는 메세지를 텔레그램 BOT API를 이용하여 알림!
			try {
				tst.sendGet("406호 나는 정재균이다.", noticeVO.getBoTitle());
			} catch (IOException e) {
				e.printStackTrace();
			}
			res = ServiceResult.OK;
		} else {
			res = ServiceResult.FAILED;
		}
		return res;
	}
	
	@Override
	public NoticeVO selectNotice(int boNo) {
		noticeMapper.incrementHit(boNo);
		return noticeMapper.selectNotice(boNo);
	}
	
	@Override
	public ServiceResult updateNotice(NoticeVO notice) {
		ServiceResult res = null;
		int status = noticeMapper.updateNotice(notice);
		if (status > 0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAILED;
		}
		return res;
	}

}
