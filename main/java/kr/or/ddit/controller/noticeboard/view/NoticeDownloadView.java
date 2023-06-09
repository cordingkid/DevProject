package kr.or.ddit.controller.noticeboard.view;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

public class NoticeDownloadView extends AbstractView{
	/*
	 	AbstractView 얘를 상속 받는 순간 예가 페이지가 된다.!
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 워닝뜰떄 얘는 문제 없으니까 체크 안해도되 하는어노테이션임
		@SuppressWarnings("unchecked")
		Map<String, Object> noticeFileMap = (Map<String, Object>) model.get("noticeFileMap");

		File saveFile = new File(noticeFileMap.get("fileSavepath").toString());

		String fileName = (String) noticeFileMap.get("fileName");

		String agent = request.getHeader("User-Agent");

		// 파일을 내보낼떄 한글이 깨질까봐
		if (StringUtils.containsIgnoreCase(agent, "msie") ||
				StringUtils.containsIgnoreCase(agent, "trident")) {
			fileName = URLEncoder.encode(fileName, "UTF-8");// msie trident IE chrome
		} else {
			fileName = new String(fileName.getBytes(), "ISO-8859-1"); // firefox, chrome
		}

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setHeader("Content-Length", noticeFileMap.get("fileSize").toString());

		// try-with-resource 자동으로 클로스 해줌
		// () 안에 객체를 finally
		try (OutputStream os = response.getOutputStream();) {
			FileUtils.copyFile(saveFile, os);
		}
	}

}




















