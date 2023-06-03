package kr.or.ddit.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
	public String download01(HttpSession session) throws Exception {
		log.info("download01() 실행...!");
		TestUser user = (TestUser) session.getAttribute("user");
		
		session.getServletContext().getRealPath("/resources/pdf/");

		String pdfPath = "d:/D_Other/asdsadsa.pdf";
		String outputFilePath = "d:/D_Other/ppddff/" + user.getNo() + "재학증명서.pdf";
//		InputStream in = null;
//		ResponseEntity<byte[]> entity = null;
//		HttpHeaders headers = new HttpHeaders();
//		ResponseEntity<byte[]>

//		String fontFace = "HYGoThic-Medium";
//		String fontNameH = "UniKS-UCS2-H";
		// ===============================================================================================================
		OutputStream fos = new FileOutputStream(new File(outputFilePath));

		PdfReader pdfReader = new PdfReader(pdfPath);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
		
		for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
			PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);

			// 한글 추가
//			BaseFont korFont = BaseFont.createFont(fontFace,fontNameH,BaseFont.NOT_EMBEDDED);
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
	        
	        
			
			System.out.println("추가된 텍스트 " + outputFilePath);

			pdfContentByte.endText();
		}
		// ===============================================================================================================
		
		pdfStamper.close();

		System.out.println("PDF 문서에 텍스트가 추가되었습니다.");

		return "itext/success";
	}

}
