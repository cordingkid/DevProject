package kr.or.ddit.itext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import kr.or.ddit.service.ItextService;
import kr.or.ddit.vo.TestUser;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/itext")
@Slf4j
public class ItextController {
	
	@Inject
	private ItextService service;

	@GetMapping("/test01")
	public String itextText01() {
		return "itext/test01";
	}

	@PostMapping("/test01")
	public String login(TestUser user, HttpSession session) {
		TestUser loginUser = service.login(user);
		session.setAttribute("user", loginUser);
		return "redirect:/itext/test02";
	}

	@RequestMapping(value = "/test02", method = RequestMethod.GET)
	public String test02() {
		return "itext/test02";
	}

	@RequestMapping(value = "/download01", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download01(HttpSession session, HttpServletRequest req){
		log.info("download01() 실행...!");
		ResponseEntity<byte[]> entity = null;
	    TestUser user = (TestUser) session.getAttribute("user");

	    // pdf 경로 설정
	    String pdfPath = session.getServletContext().getRealPath("/resources/pdf/") + "certificate.pdf";

	    log.info("피디에프 경로: " + pdfPath);
	    String pdfName = user.getNo() + "재학증명서.pdf";
	    String outputFilePath = session.getServletContext().getRealPath("/resources/pdf/") + pdfName;
	    log.info("피디에프 배포 경로: " + outputFilePath);

	    OutputStream fos = null;
	    PdfReader pdfReader = null;
	    PdfStamper pdfStamper = null;
		// ===============================================================================================================
		// pdf 생성  코드
		try {
			fos = new FileOutputStream(new File(outputFilePath));
			pdfReader = new PdfReader(pdfPath);
			pdfStamper = new PdfStamper(pdfReader, fos);
			
			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);

				BaseFont korFont = BaseFont.createFont("c:/windows/fonts/MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				
				// 기존 PDF에 텍스트 추가
				// 0, 800은 pdf 페이지 의 왼쪽 상단에 텍스트를 씁니다.
				// 0, 0은 pdf 페이지 의 왼쪽 하단에 텍스트를 씁니다.
				pdfContentByte.beginText();
				
				pdfContentByte.setFontAndSize(korFont,35); // 글꼴 및 크기 설정
				pdfContentByte.setTextMatrix(170, 650);
				pdfContentByte.showText("재  학  증  명  서");			
				
				pdfContentByte.setFontAndSize(korFont, 15);
			    pdfContentByte.setTextMatrix(210, 500);
			    pdfContentByte.showText("학           번 : " + user.getNo());
			    
			    pdfContentByte.setTextMatrix(210, 470);
			    pdfContentByte.showText("이           름 : "+user.getUserName());
			    
			    pdfContentByte.setTextMatrix(210, 440);
			    pdfContentByte.showText("주민등록번호 : " + user.getRrn().substring(0,6)+" - *******");
			    
			    pdfContentByte.setTextMatrix(210, 410);
			    pdfContentByte.showText("소           속 : " + user.getDepartment());
			    
			    pdfContentByte.setTextMatrix(210, 380);
			    pdfContentByte.showText("학           년 : " + user.getGrade());
			    
			    
			    pdfContentByte.setFontAndSize(korFont, 20);
			    pdfContentByte.setTextMatrix(210, 280);
			    pdfContentByte.showText("위의 사실을 증명함.");
			    
			    
			    pdfContentByte.setFontAndSize(korFont, 18);
			    pdfContentByte.setTextMatrix(210, 220);
			    Date date = new Date();
			    SimpleDateFormat sdate = new SimpleDateFormat("yyyy 년 MM 월 dd 일");
			    pdfContentByte.showText(sdate.format(date));
			    
				pdfContentByte.endText();
			}
			// ===============================================================================================================
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			try {
	            if (pdfStamper != null) {
	                pdfStamper.close();
	            }
	            if (pdfReader != null) {
	                pdfReader.close();
	            }
	            if (fos != null) {
	                fos.close();
	            }
	        }catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("PDF 문서에 텍스트가 추가되었습니다.");
		
		// 파일 미리보기 코드
		/*
		 * HttpHeaders headers = new HttpHeaders(); InputStream in = null;
		 * ResponseEntity<byte[]> entity = null; try { in = new
		 * FileInputStream(outputFilePath);
		 * headers.setContentType(MediaType.APPLICATION_PDF); entity = new
		 * ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		 * } catch (Exception e) { e.printStackTrace(); entity = new
		 * ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST); }
		 */
		HttpHeaders headers = new HttpHeaders();
		InputStream in = null;

		try {
			// 파일을 읽기 위해 FileInputStream을 생성합니다.
			in = new FileInputStream(outputFilePath);

			// 응답 헤더를 설정합니다.
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(pdfName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

			// ResponseEntity를 사용하여 응답 본문과 헤더를 포함한 HTTP 응답을 생성합니다.
			// IOUtils.toByteArray(in)를 사용하여 InputStream을 바이트 배열로 변환하여 응답 본문으로 설정합니다.
			// HttpStatus.CREATED를 사용하여 상태 코드 201(CREATED)을 설정합니다.
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	@GetMapping("/viewer")
	public String viewer() {
		return "itext/viewer";
	}

}
