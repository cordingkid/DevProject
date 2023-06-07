package kr.or.ddit.controller.file.item02;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.controller.file.item02.service.ItemService2;
import kr.or.ddit.vo.Item2;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/item2")
public class FileUploadController02 {

	/*
	 	3. 여러개읜 이미지 업로드 진행
	 	
	 		- 한번에 여러개의 이미지를 업로드 하는 파일 업로드 기능을 구현한다.
	 */
	
	@Inject
	private ItemService2 itemService;
	
	@Resource(name = "uploadPath") 
	private String resourcesPath;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Item2> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item2/list";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String itemRegisterForm(Model model) {
		return "item2/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String itemRegister(Item2 item,Model model) throws IOException {
		List<MultipartFile> pictures = item.getPictures();
		
		for (int i = 0; i < pictures.size(); i++) {
			MultipartFile file = pictures.get(i);
			
			String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
			if (i == 0) {
				item.setPictureUrl(savedName);
			}else if(i == 1){
				item.setPictureUrl2(savedName);
			}
		}
		itemService.register(item);
		model.addAttribute("msg", "등록이 완료 되었습니다.");
		return "item2/success";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modifyForm(int itemId, Model model) {
		Item2 item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item2/modify";
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
	
	
	@RequestMapping(value = "/display2")
	public ResponseEntity<byte[]> displayFile2(int itemId) throws FileNotFoundException{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		String fileName = itemService.getPicture2(itemId);
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
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Item2 item, Model model) throws IOException {
		List<MultipartFile> pictures = item.getPictures();
		
		for(int i = 0; i < pictures.size(); i++) {
			MultipartFile file = pictures.get(i);
			
			if(file != null && file.getSize() > 0) {
				log.info("파일 이름 : "  + file.getOriginalFilename());
				
				 String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
				 if(i == 0) {
					 item.setPictureUrl(savedName);
				 } else if(i == 1) {
					 item.setPictureUrl2(savedName);
				 }
			}
		}
		
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료되었습니다.");
		return "item2/success";
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String item2RemoveForm(int itemId, Model model) {
		Item2 item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item2/remove";
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String item2Remove(int itemId, Model model) {
		itemService.remove(itemId);
		model.addAttribute("msg", "정상적으로 삭제 하였습니다.");
		return "item2/success";
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
}
