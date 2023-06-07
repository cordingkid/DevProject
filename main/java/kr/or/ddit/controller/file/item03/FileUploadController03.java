package kr.or.ddit.controller.file.item03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.controller.file.item03.service.ItemService3;
import kr.or.ddit.vo.Item3;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/item3")
@Slf4j
public class FileUploadController03 {
	/*
	 * [드디어 비동기 방식 업로드] - 비동기 방식으로 여러개의 이미지를 업로드 하는 파일 업로드 기능을 구현한다.
	 * 
	 * 1. 환경설정
	 * 
	 * 1-1) 의존 관계 정의 - commons-io : 파일을 처리하기 위한 의존 라이브러리 - imgscalr-lib : 이미지 변환을
	 * 처리하기 위한 의존 라이브러리 - jaclson-databind : json 데이터 바인딩을 위한 의존 라이브러리
	 *** 
	 * MAVEN > Update Projects 진행하여 의존 등록 완료 (설정)
	 */

	// root-context.xml 에서 설정한 uploadPath 빈등록 path 경로를 사용.

	@Inject
	private ItemService3 itemService;

	@Resource(name = "uploadPath")
	private String resourcePath;

	@GetMapping("/register")
	public String itemRegisterForm() {
		return "item3/register";
	}

	// uploadAjax
	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws IOException {
		log.info("업로드 아작스 들어오니?");
		log.info("originalName : " + file.getOriginalFilename());
		String savedName = UploadFileUtils.uploadFile(resourcePath, file.getOriginalFilename(), file.getBytes());
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String item3Register(Item3 item, Model model) {
		String[] files = item.getFiles();

		for (int i = 0; i < files.length; i++) {
			log.info("files[" + i + "] : " + files[i]);
		}

		itemService.register(item);
		model.addAttribute("msg", "등록이 완료되었습니다.!!");
		return "item3/success";
	}

	@RequestMapping(value = "/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws FileNotFoundException {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		log.info("filename : " + fileName);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(resourcePath + fileName);
			if (mType != null) {
				headers.setContentType(mType);
			} else {
				fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" 
								+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String item3Lits(Model model) {
		List<Item3> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item3/list";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String item3ModifyForm(int itemId, Model model) {
		Item3 item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item3/modify";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String item3Modify(Item3 item, Model model) {
//		String[] files = item.getFiles();
		
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료되었습니다.!");
		return "item3/success";
	}
	
	// JSON GET 방식으로 요청
	@ResponseBody
	@RequestMapping(value = "/getAttach/{itemId}")
	public List<String> getAttach(@PathVariable("itemId") int itemId){
		log.info("겟 어태취!");
		return itemService.getAttach(itemId);
	}
	
	@GetMapping("/remove")
	public String itemRemoveForm(int itemId, Model model) {
		Item3 item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item3/remove";
	}
	
	@PostMapping("/remove")
	public String itemRemove(Item3 item, Model model) {
		itemService.remove(item);
		model.addAttribute("msg", "삭제가 완료되었습니다.");
		return "item3/success";
	}
	

}




















