package kr.or.ddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ajax")
@Slf4j
public class AjaxMemberFileController {

//	@RequestMapping(value = "/registerFileForm", method = RequestMethod.GET)
//	@PostMapping(value = "/registerFileForm", )
	@GetMapping(value = "/registerFileForm")
	public String ajaxRegisterFileForm() {
		return "member/ajaxRegisterFile";
	}
	
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain; charset=utf-8;")
	public ResponseEntity<String> uploadAjax(MultipartFile file){
		String originalFilename = file.getOriginalFilename();
		log.info("uploadAjax() 실행...!!");
		log.info("originalFilename : " + originalFilename);
		return new ResponseEntity<String>("UPLOAD SUCCESS", HttpStatus.OK);
	}
}
