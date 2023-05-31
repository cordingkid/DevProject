package kr.or.ddit.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Address;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JSPHomeController<E> {
	/*
	  7장 : JSP
	  
	  1. 지시자
	   - 지시자(directive) : JSP페이지에 대한 설정 정보를 지정할때 사용
	   - JSP가 제공하는 지시자에는 page지시자, taglib지시자, include 지시자로 3가지가 있음
	   
	   	1) page 지시자
	    	 - JSP 페이지에 대한 정보를 지정한다.
	    
	    	
	     			속성		|		설명
	     	------------------------------------------------------------------------------------
	     	contentType			JSP가 생성할 문서의 MIME 타입과 문자 코드를 지정
	     	pageEncoding			JSP 페이지 자체의 문자 코드를 지정
	     	session				JSP 페이지가 세션을 사용할지의 여부를 지정
	     	import				JSP 페이지에서 사용할 자바 클래스를 지정
	     
	    	2) taglib 지시자
	    	 - JSP 페이지에서 사용할 태그 라이브러리를 지정
	    
	    		속성		|		설명
	     	------------------------------------------------------------------------------------
	    		prefix			태그 라이브러리를 호출할 떄 사용할 접두어 지정
	    		uri				TLD 파일이 위치한 URI 혹은 파일 경로를 지정
	    	
	    	3) include 지시자
	    	 - JSP 페이지의 특정 영여에 다른 문서를 포함
	    	
	    		속성		|		설명
	     	------------------------------------------------------------------------------------
	    		file			포함할 파일의 경로를 지정
	    		
	    2. 스크립틀릿
	     - 스크립틀릿은 JSP페이지 에서 자바 코드를 실행할 때 사용하는 코드의 블록이다.
	     - 스크립틀릿 코드 블록은 <%%> 사이 그리고 <%=%> 사이에 자바 코드를 포함 가능
	     - 현재는 스크립틀릿을 사용하기 보다 JSTL과 같은 커스텀 태그라이브러리와 EL을 조합해서 JSP를 구현하는 방법을 많이 사용한다.
	     
	    3. 커스텀 태그 라이브러리
	     - 스클립트 요소가 많아지면 많아 질수록 JSP 코드는 복잡해진다.
	       이 문제를 해결하는 한가지 방법은 커스텀 태그를 사용하는것!
	       커스텀 태그를 사용하면 뷰를 표시하기 위한 로직을 공통화 하거나,표현하기 복잡한 로직을 캡슐화 할수 있어 JSP의 구현 코드를 간결하게 만들수 있다.
		   그리고, 커스텀 태그를 모아 놓은것을 커스텀 태그 라이브러리라고 함
		
		# 대표적인 태그 라이브러리
		 - JSTL(javaServer pager standard tag library)
		 - spring-form jsp tag library
		 - spring jsp tag library
		 - spring security jsp tag library
		 
		4. 표현언어 (EL)
		 - JSP는 EL(Expression Language)이라는 표현 언어를 사용해 값의 참조, 출력, 연산이 가능
		 - EL식은${} 또는 #{} 형식으로 작성
		 
		# EL을 사용하여 객체를 소화하는 방법은 다음과 같다
		 - 자바빈즈 프러퍼티를 조회하는 경우 속성명 프로퍼티명을 지정
		 - List나 배열 요소를 조회하는경우 속성명[요소위치]를 지정
		 - Map 요소를 조회하는경우 속성명.키명 또는 속성명[키명]을 지정
		 
		# 사용 가능한 연산자
		 - EL에서는 다음과 같은 연산자를 사용할 수있다.
		 
		 	# 산술 연산자
		 		
		 		+	|	-	|	*	|	/	|	%
		 	--------------------------------------------
		 	
		 	# 비교 연산자
		 		연산자	|	설명
	       -----------------------------------------------------
	       ==(eq)			같은 값인지 비교
	       !=(ne)			다른값인지 비교
	       <=(le)			왼쪽이 작거나 같은 값인지 비교
	       >=(ge)			왼쪽이 크거나 같은 값인지 비교
	       <(lt)			왼쪽이 작은값인지 비교
	       >(gt)			왼쪽이 큰값인지 비교
	       
	       
	       # empty 연산자
	       - null이거나 공백(문자열의 경우 공백 문자)인지 비교
	       
	       [true 소견::::]
	       - null 값, 빈 문자열(""), 길이가 0인 배열, 빈 collection
	       
	       # 논리 연산자
	       		연산자		|		설명
	       	------------------------------------------------------------------------------
	       	&&(and)				두 피연산자 모두 true이면 bool true를 반환하고 아니면 false반환
	       	||(or)				두 피연산자 중 하나 또는 모드 true이면 bool true를 반환하고 아니면 false반환
	       	!(not)				해당 피연산자의 의미를 반대로 바꿈
	 */
	
	@RequestMapping(value = "/home0101", method = RequestMethod.GET)
	public String home0101(Model model) {
		Member member = new Member();
		member.setUserId("hong");
		member.setPassword("123");
		member.setEmail("aaa@naver.com");
		member.setUserName("홀롤롤");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2023);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		
		member.setDateOfBirth(cal.getTime());
		model.addAttribute(member);
		return "home/home0101";
	}
	
	@RequestMapping(value = "/home0102", method = RequestMethod.GET)
	public String home0102(Model model) {
		Member member = new Member();
		
		String[] hobbyArr = {"Music", "Movie"};
		member.setHobbyArray(hobbyArr);
		
		List<String> list = new ArrayList<String>();
		list.add("Music");
		list.add("Movie");
		member.setHobbyList(list);
		
		model.addAttribute(member);
		return "home/home0102";
	}
	
	@RequestMapping(value = "/home0103", method = RequestMethod.GET)
	public String home0103(Model model) {
		Member member = new Member();
		
		Address address = new Address();
		address.setPostCode("090122");
		address.setLocation("Incheon");
		member.setAddress(address);
		
		model.addAttribute(member);
		return "home/home0103";
	}
	
	@RequestMapping(value = "/home0104", method = RequestMethod.GET)
	public String home0104(Model model) {
		Map<String, String> memberMap = new HashMap<String, String>();
		
		memberMap.put("id", "asd");
		memberMap.put("pwd", "123");
		memberMap.put("email", "asd@naver.com");
		memberMap.put("name", "홍길동");
		
		// memberMap 이라는 키를 정확하게 명시 했기때문에 jsp 페이지에서 명시된 키로 데이터를 출력할수있다.
		model.addAttribute("memberMap", memberMap);
		return "home/home0104";
	}
	
	@RequestMapping(value = "/home0201", method = RequestMethod.GET)
	public String home0201(Model model) {
		int coin = 100;
		
		
		model.addAttribute("coin", coin);
		return "home/home0201";
	}
	
	@RequestMapping(value = "/home0202", method = RequestMethod.GET)
	public String home0202(Model model) {
		int coin = 1000;
		
		model.addAttribute("coin", coin);
		return "home/home0202";
	}
	
	@RequestMapping(value = "/home0203", method = RequestMethod.GET)
	public String home0203(Model model) {
		String userId = "asd";
		model.addAttribute("userId", userId);
		return "home/home0203";
	}
	
	// empty 연산자 사용
	@RequestMapping(value = "/home0301", method = RequestMethod.GET)
	public String home0301(Model model) {
		
		return "home/home0301";
	}
	
	@RequestMapping(value = "/home0302", method = RequestMethod.GET)
	public String home0302(Model model) {
		Member member = new Member();
		model.addAttribute("member", member);
		return "home/home0301";
	}
	
	@RequestMapping(value = "/home0401", method = RequestMethod.GET)
	public String home0401(Model model) {
		int coin = 1000;
		String userId = "hongkd";
		
		Member member = new Member();
		model.addAttribute("member", member);
		model.addAttribute("coin", coin);
		model.addAttribute("userId", userId);
		return "home/home0401";
	}
}































