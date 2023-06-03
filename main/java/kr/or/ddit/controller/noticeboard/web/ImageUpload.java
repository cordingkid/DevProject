package kr.or.ddit.controller.noticeboard.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ImageUpload {

	@RequestMapping(value = "/imageUpload.do")
	public @ResponseBody String imageUpload(HttpServletRequest req, HttpServletResponse resp,
			MultipartHttpServletRequest multiFile) throws IOException {
//		HttpServletRequest 업로드 할경로 설정
//		HttpServletResponse 응답으로 마인 타입 설정할떄
//		MultipartHttpServletRequest 이미지파일 받아 내고자 하는 변수
		
		log.info("imageUpload 실행!!!!!!!!!!!!!!!!!!!");
		
		JsonObject json = new JsonObject();
		PrintWriter pw = null;
		OutputStream out = null;
		long limitSize = 1024 * 1024 * 2; // 2MB
		MultipartFile file = multiFile.getFile("upload");

		if (file != null && file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) { // 파일이 있을때
			if (file.getContentType().toLowerCase().startsWith("image/")) {
				if (file.getSize() > limitSize) {
					JsonObject jsonMsg = new JsonObject();
					JsonArray jsonArr = new JsonArray();
					jsonMsg.addProperty("message", "2MB미만의 이미지만 업로드 가능합니다.");
					jsonArr.add(jsonMsg);
					json.addProperty("uploaded", 0);
					json.add("error", jsonArr.get(0));

					resp.setCharacterEncoding("UTF-8");
					pw = resp.getWriter();
					pw.print(json);
				} else {
					try {
						String fileName = file.getName();
						byte[] bytes = file.getBytes();
						String uploadPath = req.getServletContext().getRealPath("/resources/img"); // 업로드 경로(서버)

						File uploadFile = new File(uploadPath);
						if (!uploadFile.exists()) {
							uploadFile.mkdirs(); // 존재 하지 않는 경우 폴더 구조 만들어주고
						}

						fileName = UUID.randomUUID().toString(); // 랜덤으로 발생되어 만들어진 문자열값을 지정
						uploadPath = uploadPath + "/" + fileName + ".jpg"; // /resources/img/UUID.jpg

						out = new FileOutputStream(new File(uploadPath));
						out.write(bytes);

						pw = resp.getWriter();
						resp.setContentType("text/html");
						String fileUrl = req.getContextPath() + "/resources/img/" + fileName + ".jpg";

						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl);

						pw.print(json);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (out != null) {
							out.close();
						}
						if (pw != null) {
							pw.close();
						}
					}
				}
			}
		}
		return null;
	}
}
