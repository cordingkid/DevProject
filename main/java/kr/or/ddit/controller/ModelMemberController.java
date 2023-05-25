package kr.or.ddit.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Address;
import kr.or.ddit.vo.Card;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ModelMemberController<E> {
	
	/*
	 * 6장. 데이터 전달자 모델
	 *  - Model 객체이용
	 *  
	 *  1) 매개변수에 선언된 Model 객체의 addAttribute() 매서드를 호출하여 데이터를 뷰 에 전달
	 */
	
	@RequestMapping(value = "/read01",method = RequestMethod.GET)
	public String read01(Model model) {
		log.info("read01() 실행...!");
		
		model.addAttribute("userId", "worbs");
		model.addAttribute("password", "1234");
		model.addAttribute("email", "aaa@ccc.com");
		model.addAttribute("userName", "정재균");
		model.addAttribute("birthDay", "1999-10-14");
		return "member/read01";
	}
	
	// 2) Model 객체에 자바빈즈 클래스 객체를 값으로만 전달할떄는 뷰에서 참조할 이름을 클래스명의 앞글자 소문자로 변홚여 처리
	@GetMapping("/read02")
	public String read02(Model model) {
		log.info("read02() 실행...!");
		
		Member member = new Member();
		member.setUserId("worbs");
		member.setPassword("1234");
		member.setUserName("정재균");
		member.setEmail("aaa@ccc.com");
		member.setBirthDay("1999-10-14");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1999);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 14);
		member.setDateOfBirth(cal.getTime());
		
		model.addAttribute(member);
		return "member/read02";
	}
	
	// Model 객체에 자바빈즈 클래스 객체를 특정한 이름을 지정하여 전달할수있다.
	@GetMapping("/read03")
	public String read03(Model model) {
		log.info("read03() 실행...!");
		
		Member member = new Member();
		member.setUserId("worbs");
		member.setPassword("1234");
		member.setUserName("정재균");
		member.setEmail("aaa@ccc.com");
		member.setBirthDay("1999-10-14");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1999);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 14);
		member.setDateOfBirth(cal.getTime());
		
		model.addAttribute("user",member);
		return "member/read03";
	}
	
	// Model 객체에 자바빈즈 클래스 객체를 특정한 이름을 지정하여 전달할수있다.
	@GetMapping("/read04")
	public String read04(Model model) {
		log.info("read04() 실행...!");
		
		String [] carArray = {"jeep", "bmw"};
		
		List<String> carList = new ArrayList<String>();
		carList.add("jeep");
		carList.add("volvo");
		
		String [] hobbyArray = {"Music", "Movie"};
		
		List<String> hobbyList = new ArrayList<String>();
		hobbyList.add("Music");
		hobbyList.add("Movie");
		
		model.addAttribute("carArray",carArray);
		model.addAttribute("carList",carList);
		model.addAttribute("hobbyArray",hobbyArray);
		model.addAttribute("hobbyList",hobbyList);
		
		return "member/read04";
	}
	
	// Model 객체를 통해 중첩된 자바빈즈 클래스 전달 가능
	@GetMapping("/read05")
	public String read05(Model model) {
		log.info("read05() 실행...!");
		
		Member member = new Member();
		
		Address address = new Address();
		address.setPostCode("999999");
		address.setLocation("Seoul");
		
		member.setAddress(address);
		
		List<Card> cardList = new ArrayList<>();
		
		Card card1 = new Card();
		card1.setNo("123456");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 14);
		card1.setValidMonth(cal.getTime());
		
		cardList.add(card1);
		
		Card card2 = new Card();
		card2.setNo("123456");
		
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		card2.setValidMonth(cal.getTime());
		
		cardList.add(card2);
		
		member.setCardList(cardList);
		model.addAttribute("user", member);
		return "member/read05";
	}
	
	// 6) Model 객체를 통해 다양한 타입의 값을 전달할수 있다.
	@RequestMapping(value = "/read06", method = RequestMethod.GET)
	public String read06(Model model) {
		log.info("read06() 실행...!");
		
		Member member = new Member();
		member.setUserId("worbs");
		member.setPassword("1234");
		member.setEmail("aaa@ccc.com");
		member.setUserName("정재균");
		member.setBirthDay("1999-10-14");
		member.setGender("강한남자");
		member.setForeginer(true);
		member.setNationality("Korea");
		member.setCars("Range Rover");
		member.setDeveloper("개발자");
		member.setHobby("드라이브, 코딩");
		
		String[] carArr = {"jeep", "bmw"};
		member.setCarArray(carArr);
		
		List<String> carList = new ArrayList<>();
		carList.add("bmw");
		carList.add("audi");
		member.setCarList(carList);
		
		String[] hobbyArr = {"Music", "Movie"};
		member.setHobbyArray(hobbyArr);
		
		
		List<String> hobbyList = new ArrayList<>();
		hobbyList.add("Music");
		hobbyList.add("Movie");
		member.setHobbyList(hobbyList);
		
		List<Card> cardList = new ArrayList<>();
		
		Card card1 = new Card();
		card1.setNo("123456");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 14);
		card1.setValidMonth(cal.getTime());
		
		cardList.add(card1);
		
		Card card2 = new Card();
		card2.setNo("123456");
		
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		card2.setValidMonth(cal.getTime());
		
		cardList.add(card2);
		
		member.setCardList(cardList);
		
		cal.set(Calendar.YEAR, 1999);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 14);
		member.setDateOfBirth(cal.getTime());
		
		Address address = new Address();
		address.setPostCode("999999");
		address.setLocation("Seoul");
		
		member.setAddress(address);
		
		member.setIntroduction("안녕 <br>반가워");
		model.addAttribute("user", member);
		return "member/read06";
	}
	
}















