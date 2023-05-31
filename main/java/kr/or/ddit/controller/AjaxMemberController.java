package kr.or.ddit.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Address;
import kr.or.ddit.vo.Card;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ajax")
@Slf4j
public class AjaxMemberController {
	
	@RequestMapping(value = "/registerForm", method = RequestMethod.GET)
	public String ajaxRegisterForm() {
		log.info("ajaxRegisterForm() 실행...!");
		return "member/ajaxRegisterForm";
	}
	
	// 1) URL 경로 상의 경로 변수 값을 @@PathVariable 로 지정하여 문자열 매개변수로 처리
	@RequestMapping(value = "/register/{userId}")
	public ResponseEntity<String> ajaxRegister01(@PathVariable("userId") String userId){
		log.info("ajaxRegister01() 실행...!");
		log.info("userId : " + userId);
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		return entity;
	}
	
	
	// 2) URL 경로 상의 여러개의 경로 변수값을 @Pathvariable 어노테이션을 지정하여 문자열 매개변수로 처리
	@RequestMapping(value = "/register/{userId}/{password}", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister02(
			@PathVariable("userId") String userId,
			@PathVariable("password") String password){
		log.info("ajaxRegister02() 실행...!");
		log.info("userId : " + userId);
		log.info("password : " + password);
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		return entity;
	}
	
	//3) 객체 타입의 JSON 요청 데이터 @RequestBody 어노테이션을 지정하여 자바빈즈 매개변수로 처리
	@RequestMapping(value = "/register03", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister03(
			@RequestBody Member member){
		log.info("ajaxRegister03() 실행...!");
		log.info("userId : " + member.getUserId());
		log.info("password : " + member.getPassword());
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	// 4) 객체 타입의 JSON 요청 데이터는 문자열 매개변수로 처리할수 없다.
	@RequestMapping(value = "/register04", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister04(String userId){
		// 요청 본문에서 데이터가 바인딩 되지 않아 userId가 null이 나온다.
		// 요청 본문에서 데이터를 가져오려면 @RequestBody 어노테이션을 붙여야한다.
		log.info("ajaxRegister04() 실행...!");
		log.info("userId : " + userId);
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	//5) 요청 URL에 쿼리 파라미터를 붙여서 전달 하면 문자열 매개변수로 처리
	@RequestMapping(value = "/register05", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister05(String userId, String password){
		// userId : 쿼리스트림에 담겨져 오는 데이터 이기 때문에 일반적인 방식으로도 데이터를 받을수 있지만
		// password는 요청본문에서 데이터를 바인딩해 받아오지 못하므로 null이다.
		log.info("ajaxRegister05() 실행...!");
		log.info("userId : " + userId);
		log.info("password : " + password);
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register06/{userId}", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister06(
			@PathVariable("userId") String userId, 
			@RequestBody Member member){
		log.info("ajaxRegister06() 실행...!");
		log.info("userId : " + userId);
		log.info("member.getUserId() : " + member.getUserId());
		log.info("password : " + member.getPassword());
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register07", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister07(
			@RequestBody List<Member> memberList){
		/*
		 * 비동기 처리시, List 컬렉션으로 데이터를 받을때에는 @RequestBody 어노테이션을 이용
		 * 동기 처리시에는 컨트롤러 매서드 내에서  List 타입으로 값을 바인딩 할수 없지만
		 * 객채 내 존재하는 List 타입으로는 데이터를 바인딩가능하다.
		 * 동기와 비동기 처리시의 차이점을꼭 기억하자
		 */
		
		log.info("ajaxRegister07() 실행...!");
		
		for(Member mem : memberList) {
			log.info("mem.getUserId() : " + mem.getUserId());
			log.info("mem.getPassword() : " + mem.getPassword());
		}
		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register08", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister08(
			@RequestBody Member member){
		log.info("ajaxRegister08() 실행...!");
		log.info("userId : " + member.getUserId());
		log.info("password : " + member.getPassword());
		
		Address address = member.getAddress();
		if(address != null) {
			log.info("address.getPostCode() : " + address.getPostCode());
			log.info("address.getLocation() : " + address.getLocation());
		}else {
			log.info("address is null");
		}
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register09", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister09(
			@RequestBody Member member){
		log.info("ajaxRegister09() 실행...!");
		log.info("userId : " + member.getUserId());
		log.info("password : " + member.getPassword());
		
		List<Card> cardList = member.getCardList();
		
		
		
		if(cardList != null && cardList.size() > 0) {
			for (Card card : cardList) {
				log.info("카드 번호  : " + card.getNo());
				log.info("카드 유효  : " + card.getValidMonth());
			}
		}
		log.info("member.getDateOfBirth() : " + member.getDateOfBirth());
		Address address = member.getAddress();
		if(address != null) {
			log.info("address.getPostCode() : " + address.getPostCode());
			log.info("address.getLocation() : " + address.getLocation());
		}else {
			log.info("address is null");
		}
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
}















