package kr.or.ddit.controller.noticeboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mapper.LoginMapper;
import kr.or.ddit.mapper.NoticeMapper;
import kr.or.ddit.mapper.ProfileMapper;
import kr.or.ddit.vo.DDITMemberVO;
import kr.or.ddit.vo.NoticeFileVO;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeServiceImpl implements INoticeService {

	@Inject
	private NoticeMapper noticeMapper;

	@Inject
	private LoginMapper loginMapper;
	
	@Inject
	private ProfileMapper profileMapper;

//	TelegramSendController tst = new TelegramSendController();

	@Override
	public ServiceResult insertNotice(HttpServletRequest req, NoticeVO noticeVO) {
		ServiceResult res = null;
		int status = noticeMapper.insertNotice(noticeVO);
		if (status > 0) {
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			try {
				noticeFileUpload(noticeFileList, noticeVO.getBoNo(), req);
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
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
	public ServiceResult updateNotice(HttpServletRequest req, NoticeVO notice) {
		ServiceResult res = null;
		int status = noticeMapper.updateNotice(notice);
		if (status > 0) {
			List<NoticeFileVO> noticeFileList = notice.getNoticeFileList();

			try {
				// 수정을 위해서 새로 추가된 파일 데이터를 먼저 업로드 처리를 한다.
				noticeFileUpload(noticeFileList, notice.getBoNo(), req);
				
				// 삭제를 원하는 파일번호를 가져와서
				Integer[] delNoticeNo = notice.getDelNoticeNo();
				if (delNoticeNo != null) {
					// 삭제를 원하는 파일 번호들 하나하나를 데이터베이스에서 가져와서 vo 저장시키고
					// 그다음 데이터베이스 삭제후
					// 서버 업로드 경로에 있는 파일도 삭제를 한다.
					for (int i = 0; i < delNoticeNo.length; i++) {
						NoticeFileVO noticeFileVO = noticeMapper.selectNoticeFile(delNoticeNo[i]);
						noticeMapper.deleteNoticeFile(delNoticeNo[i]);
						File file = new File(noticeFileVO.getFileSavepath());
						file.delete();
					}
				}
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
			}
			
			res = ServiceResult.OK;
		} else {
			res = ServiceResult.FAILED;
		}
		return res;
	}

	@Override
	public ServiceResult deleteNotice(HttpServletRequest req, int boNo) {
		ServiceResult res = null;
		
		// 아래에서 boNo에 해당하는 배열 데이터를 삭제 할떄 사용할 객체(noticeVO)
		NoticeVO noticeVO = noticeMapper.selectNotice(boNo);
		// 본체 노티스를 삭제 하기전에 파일 데이터를 먼저 삭제하기 위한 요청
		noticeMapper.deleteNoticeFileByBoNo(boNo);
		
		int status = noticeMapper.deleteNotice(boNo);
		if (status > 0) {
			// 파일 데이터를 완벽하게 삭제처리하기
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			if (noticeFileList != null && noticeFileList.size() > 0) {
				
				String[] filePath = noticeFileList.get(0).getFileSavepath().split("/");
				String path = filePath[0];
				deleteFolder(req, path);
			}
			
			
			res = ServiceResult.OK;
		} else {
			res = ServiceResult.FAILED;
		}
		return res;
	}

	private static void deleteFolder(HttpServletRequest req, String path) {
		File folder = new File(path);
		if(folder.exists()) {
			File[] fileList = folder.listFiles();
			
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isFile()) { 					// 폴더 안의 있는 파일이 파일일때
					fileList[i].delete();
				}else {				// 폴더안에 있는 파일이 폴더 일때 재귀함수 호출(폴더안으로 들어가서 또 지움)
					deleteFolder(req, fileList[i].getPath());
				}
			}
		}
		folder.delete();	// 폴더 삭제 bono에 해당하는 폴더를 삭제
	}


	@Override
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO) {
		return noticeMapper.selectNoticeCount(pagingVO);
	}

	@Override
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO) {
		return noticeMapper.selectNoticeList(pagingVO);
	}

	@Override
	public ServiceResult idCheck(String memId) {
		ServiceResult res = null;
		DDITMemberVO member = loginMapper.idCheck(memId);
		if (member != null) {
			res = ServiceResult.EXIST;
		} else {
			res = ServiceResult.NOTEXIST;
		}
		return res;
	}

	@Override
	public ServiceResult signup(HttpServletRequest req, DDITMemberVO memberVO) {
		ServiceResult res = null;
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String profileImg = "";
		MultipartFile profileImgFile = memberVO.getImgFile();
		if(profileImgFile.getOriginalFilename() != null 
				&& !profileImgFile.getOriginalFilename().equals("")) {
			String fileName = UUID.randomUUID().toString();
			fileName += "_" + profileImgFile.getOriginalFilename();
			uploadPath += "/" + fileName;
			try {
				profileImgFile.transferTo(new File(uploadPath));// 이미지 복사
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
			}
			profileImg = "/resources/profile/" + fileName;
		}
		
		memberVO.setMemProfileImg(profileImg);
		int status = loginMapper.signup(memberVO);
		if (status > 0) {
			res = ServiceResult.OK;
		} else {
			res = ServiceResult.FAILED;
		}
		return res;
	}
	
	@Override
	public DDITMemberVO loginCheck(DDITMemberVO memberVO) {
		return loginMapper.loginCheck(memberVO);
	}
	
	@Override
	public String idForgetProcess(DDITMemberVO member) {
		return loginMapper.idForgetProcess(member);
	}
	
	@Override
	public String pwForgetProcess(DDITMemberVO member) {
		return loginMapper.pwForgetProcess(member);
	}
	
	private void noticeFileUpload(List<NoticeFileVO> noticeFileList, int boNo, HttpServletRequest req) throws IllegalStateException, IOException {
		String savePath = "/resources/notice/";
		
		if (noticeFileList != null && noticeFileList.size() > 0) {
			for (NoticeFileVO noticeFileVO : noticeFileList) {
				String saveName = UUID.randomUUID().toString();
				
				// 파일명을 설정할떄 원본 파일명의 공백을 _로 변경한다.
				saveName = saveName + "_" + noticeFileVO.getFileName().replace(" ", "_");
//				String endFileName = noticeFileVO.getFileName().split("\\.")[1]; // 확장자 디버깅 및 확장자 추출참고
				
				// /resources/notice/1
				String saveLocate = req.getServletContext().getRealPath(savePath+boNo);
				File file = new File(saveLocate);
				
				// 정재균 안녕 ? 나는 정수라고해 너는 오늘 점심 뭐먹니 ? 
				// 우리는 화평을 간단다 ? 
				// 소선생 ? 거기 맛있어 
				
				if (!file.exists()) {
					file.mkdirs();
				}
				
				saveLocate += "/" + saveName;
				
				File saveFile = new File(saveLocate);
				
				noticeFileVO.setBoNo(boNo);
				noticeFileVO.setFileSavepath(saveLocate);
				
				noticeMapper.insertNoticeFile(noticeFileVO);
				
				noticeFileVO.getItem().transferTo(saveFile);
				
			}
		}
	}
	
	@Override
	public NoticeFileVO noticeDownload(int fileNo) {
		NoticeFileVO noticeFileVO =noticeMapper.noticeDownload(fileNo);
		if (noticeFileVO == null) {
			throw new RuntimeException();
		}
		noticeMapper.incrementNoticeDowncount(fileNo);
		return noticeFileVO;
	}
	
	
	@Override
	public DDITMemberVO selectMember(DDITMemberVO sessionMember) {
		return profileMapper.selectMember(sessionMember);
	}
	
	@Override
	public ServiceResult profileUpdate(HttpServletRequest req, DDITMemberVO memberVO) {
		
		ServiceResult res = null;
		// 사용자가 수정한 프로필 이미지 정보에따라서 이미지 정보 값을 설정후 memberVO 에 셋팅해서 전달한다.
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		String proFileImg = "";
		MultipartFile proFileImgFile = memberVO.getImgFile();
		if (proFileImgFile.getOriginalFilename() != null && !proFileImgFile.getOriginalFilename().equals("")) {
			String fileName = UUID.randomUUID().toString();
			fileName += "_" + proFileImgFile.getOriginalFilename();
			uploadPath += "/" + fileName;
			try {
				proFileImgFile.transferTo(new File(uploadPath));
			} catch (IllegalStateException | IOException e) {
				log.info(e.getMessage());
			}
			proFileImg = "/resources/profile/" + fileName;
		}
		
		// 선택한 프로피르 이미지가 존재하지않으면 공백이 넘어감
		// 업로드 되었으면 경로와 파일명으로 구성된 경로가 넘어간다.
		memberVO.setMemProfileImg(proFileImg);
		
		int status = profileMapper.profileUpdate(memberVO);
		if (status > 0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAILED;
		}
		return res;
	}
	
	
}
























 
