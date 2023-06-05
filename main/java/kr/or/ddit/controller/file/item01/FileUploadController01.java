package kr.or.ddit.controller.file.item01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.controller.file.item01.service.ItemService;
import kr.or.ddit.vo.Item;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/item")
@Slf4j
public class FileUploadController01 {
	/*
	 	13장 파일 업로드
	 	
	 	1. 파일 업로드 설명
	 	- 서블릿 3.0에서 지원하는 파일 업로드 기능과 스프링 웹에서 제공하는 컴포넌트를 사용하여 파일을 업로드 한다.
	 	
	 		파일 업로드 설정
	 		1) 서블릿 3.0 이상 지원
	 		2) 서블릿 표준 파일 업로드 기능을 활성화
	 		3) 스프링 MVC 연계
	 		4) 업로드된 파일 저장 위치 설정
	 		
	 		환경설정
	 		
	 		1) 의존 관계정의
	 		- 파일을 처리하기위해서 의존 라이브러리를 추가
	 			> pom.xml 서블릿 표준 버전 3.1로 설정
	 			
	 		2)웹 컨테이너 설정
	 			> web.xml 서블릿 표준 버전 3.1로 설정
	 			> multipart-config 활성화 (web.xml servlet 태그 내 설정)
	 		3) 스프링 웹설정
	 			>servlet-context.xml
	 			> multipartResolver Bean 등록 (id는 multipartResolver로 설정)
	 			
	 			[파일 업로드 설정]
	 			파일 업로드를 설정하는 방식은 3가지 있다.
	 			
	 			1. StandardServletMultipartResolver 사용시 설정
	 				> Servlet 3.0의 part를 이용한 multipartFile 데이터 처리
	 				> servlet-context.xml 설정
	 					> StandardServletMultipartResolver Bean 등록
	 				> web.xml 에설정
	 					> multipart-config (servlet 태그 안에 설정)
	 			2. CommonsMultipartResolver 사용시 설정( 우리가 사용할 방식임)**
	 				> Commons Fileupload API를 이용한 Multipart File 데이터 처리
	 				> bean 속성으로 maxUploadSize, maxInMemorySize, defaultEncoding 설정을 제공
	 				> 기본값 및 허용되는 값에 대한 자세한 내용은 DiskFileUpload 속성을 참조
	 				> pom.xml 설정
	 					> commons-fileupload 추가
	 				> root-context.xml 설정
 						> CommonsMultipartResolver bean 등록
 					
	 				> root-context.xml 설정
	 					> uploadPath bean 등록
 					
 			데이터 베이스 준비
 				1) item 테이블 생성 (item, item2, item3, item_attach)
 				
	 		1. 파일 업로드 구현 설명
	 		
	 			- 파일업로드 등록 화면 컨트롤러 만들기 (FileUploadController01)
	 			- 파일업로드 등록 화면 컨트롤러 매소드 만들기
	 			- 파일 업로드 등록 화면 만들기
	 			- 여기까지 확인
	 			
	 			
	 			- 파일 업로드 등록 기능 컨트롤러 메서드 만들기
	 			- 파일 업로드 등록 기능 서비스 인터페이스 메소드, 만들기
	 			- 파일 업로드 등록기능서비스 클래스 만들기
	 			- 파일 업로드 등록기능 Mapper 인터페이스 만들기
	 			- 파일 업로드 등록 기능 mapper xml 추가
	 			- 파일 업로드 등록 완료 페이지 만들기
	 			
	 			
	 			- 파입 업로드 목록화면 컨트롤러 매소드 만들기
	 			- 파일 업로드 목록 화면 서비스 인터페이스 메소드 만들기
	 			- 파일업로드 목록 화면 서비스 클래스 매소드 만들기
	 			- 파일업로드 목록화면 Mapper 인터페이스 메소드 만들기
	 			- 파일 업로드 목록 화면 Mapper xml 쿼리 만들기
	 			- 파일 업로드 목록화면 만들기
	 			- 여기까지 확인
	 			
	 			- 파일 업로드 수정 화면 컨트롤러 메소드 만들기
	 			- 파일 업로드 수정화면 서비스 인터페이으 메소드 만들기
	 			- 파일 업로드 수정 화면 서비스 클래스 매소드 만들기
	 			- 파일 업로드 수정화면 Mapper 인터페이스 매소드 만들기
	 			- 파일 업로드 수정 화면 Mapper xml 쿼리 만들기
	 			- 파일 업로드 수정 화면 만들기
	 			- 파일 업로드 수정 기능 컨트롤러 매소드 만들기
	 			- 파일 업로드 수정 기능 서비스 인터페이스 메소드 만들기
	 			- 파일 업로드 수정 기능 서비스 클래스 매소드 만들기
	 			- 파일 업로드 수정 기능 Mapper 인터페이스 매소드 만들기
	 			- 파일 업로드 수정 기능 Mapper xml 쿼리 만들기
	 			- 여기까지 확인
	 			 
	 */
	
	// root-context.xml 에서 설정한 uploadPath 빈등록 path 경로를 가져온다.
	@Resource(name = "uploadPath") 
	private String resourcesPath;
	
	@Inject
	private ItemService itemService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String itemRegisterForm() {
		return "item/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String itemRegister(Item item, Model model) throws IOException {
		MultipartFile file = item.getPicture();
		log.info("originalName : " + file.getOriginalFilename());
		log.info("size : " + file.getSize());
		log.info("contentType : " + file.getContentType());
		
		// 넘겨온 파일을 파일 업로드(복사)를 진행하고
		// return : UUID + "_" + 원본파일명
		String createdFileName = uploadFile(file.getOriginalFilename(),file.getBytes());
		item.setPictureUrl(createdFileName);
		itemService.register(item);
		model.addAttribute("msg", "등록이 완료되었습니다.");
		return "item/success";
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String itemList(Model model) {
		List<Item> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item/list";
	}
	
	@GetMapping("/modify")
	public String itemModifyForm(int itemId, Model model) {
		Item item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item/modify";
	}
	
	@PostMapping("/modify")
	public String itemModify(Item item, Model model) throws IOException {
		MultipartFile file = item.getPicture();
		
		if(file != null && file.getSize() > 0) {
			String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
			item.setPictureUrl(createdFileName);
		}
		
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료 되었습니다.");
		return "item/success";
	}
	
	@GetMapping("/remove")
	public String itemRemoveForm(int itemId, Model model) {
		Item item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item/remove";
	}
	
	@PostMapping("/remove")
	public String itemRemove(int itemId, Model model) {
		itemService.remove(itemId);
		model.addAttribute("msg", "삭제가 완료됬다.!");
		return "item/success";
	}
	
	// 파일 이미지 보여주기
	@RequestMapping(value = "/display")
	public ResponseEntity<byte[]> displayFile(int itemId) throws FileNotFoundException{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		String fileName = itemService.getPicture(itemId);
		log.info("fileName : " + fileName);
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 추출
			MediaType mType = getMediaType(formatName); // 이미지 인지 아닌지 체크 하기 위한 함수

			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(resourcesPath + File.separator + fileName); // File.separator = "/" 이거와 동일하다.
			if (mType != null) {
				headers.setContentType(mType);
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return entity;
	}
	
	private MediaType getMediaType(String formatName) {
		if (formatName != null) {
			if (formatName.toUpperCase().equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if (formatName.toUpperCase().equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}
			if (formatName.toUpperCase().equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}
		return null;
	}

	private String uploadFile(String originalFilename, byte[] fileData) throws IOException {
		log.info("resourcesPath : " + resourcesPath);
		
		UUID uuid = UUID.randomUUID(); // UUID 파일명 생성 준비
		String createdFileName = uuid.toString() + "_" + originalFilename;
		
		File file = new File(resourcesPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		// 경로를 가지고 createdFileName을 복사한다.
		File target = new File(resourcesPath, createdFileName);
		FileCopyUtils.copy(fileData, target); // 파일 복사
		return createdFileName;
	}
	
}
























