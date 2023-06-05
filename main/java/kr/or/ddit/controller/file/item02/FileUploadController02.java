package kr.or.ddit.controller.file.item02;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

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
