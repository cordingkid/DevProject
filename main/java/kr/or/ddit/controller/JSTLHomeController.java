package kr.or.ddit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/jstl")
@Slf4j
public class JSTLHomeController<E> {
	/*
	   5. 표준 태그 라이브러리(JSTL)
	    - 많은 개발자들이 JSP 에서 코드를 깔끔하게 작성하기 위해 커스텀 태그를 만들어왔는데.
	    이런 중복되는 노력을 없애기 위해서 나온것이 JSTL이다.
	    
	    
	    	1) core 태그 라이브러리
	    		
	    		요소		|		설명
	    	---------------------------------------------------------------------------------------
	    	<c:out>				JSPWrityer에 값을 적잘하게 처리한 후에 출력한다.
	    	<c:set>				JSP에서 사용할 변수 설정
	    	<c:remove>			설정한 변수를 제거한다
	    	<c:catch>			예외를 처리한다.
	    	<c:if>				조건을 처리하고 지정된 조건과 일치하는 처래 내용을 구현
	    	<c:choose>			여러 조거ㅗㄴ을 처리할떄 사용
	    	<c:when>			여러 조건을 지정하고 지정한 조건과 일치하는 처리 내용구현
	    	<c:otherwise>		else 랑 같은 동작함
	    	<c:foreach>			컬렉션이나 배열의 각 항목을 처리할떄 사용
	    	<c:forTokecns>		구문자로 구분된 각각의 토큰을 처리할떄 사용
	    	<c:import>			URL을 사용하여 다른 자원을 삽입
	    	<c:url>				URL을 재작성 한다,
	    	<c:redirect>		지정한 URL에 리다이렉트 한다.
	    	<c:param>			파라미터를 지정한다.
	    	
	    	
	    	
	    	2) fmt 태그 라이브러리
	    		
	    		요소		|		설명
	    	---------------------------------------------------------------------------------------
	    	<fmt:formatNumber>		숫자를 형식화 한다
	    	<fmt:parseNumber>		문자열을 숫자로 변환 한다.
	    	<fmt:formatDate>		Date객체를 문자열로 변환한다.
	    	<fmt:parseDate>			문자열을 Date객체로 변환한다.
	    	
	    	
	    	
	    	2) function 태그 라이브러리
	    		
	    		요소		|		설명
	    	---------------------------------------------------------------------------------------
	    	<fn:contains>			지정한 문자열이 포함되 있는지 판단.
	    	<fn:containsIgnoreCase>	지정한 문자열이 대문자/소문자를 구분하지 않고 포함돼 있는지 판단.
	    	<fn:startWith>			지정한 문자열로 시작하는 판단	
	    	<fn:endWith>			지정한 문자열로 끝나는지 판단.
	    	<fn:indexOf>			지정한 문자열이 처음으로 나왔을떄의 인덱스를 구한다.
	    	<fn:length>				컬렉션 또는 배열의 요소 개수,문자열 길이를 구함
	    	<fn:replace>			문자열을 치환한다
	    	<fn:toLowerCase>		소문자로 변환한다.
	    	<fn:toUpperCase>		대문자로 변환한다.
	    	<fn:trim>				문자열을 trim 한다.
	    	<fn:substring>			지정한 범위에 해당하는 문자열을 잘라낸다.
	    	<fn:substringAfter>		지정한 문자열에 일치하는 이전의 문자열을 잘라낸다.
	    	<fn:substringBefore>	지정한 문자열에 일치하는 이후의 문자열을 잘라낸다.
	    	<fn:join>				문자열 배열을 결합해서 하나의 문자열을 만든다.
	    	<fn:split>				문자열을 구분자로 분할해서 문자열 배열을 만든다.
	    	
	    	
	    6. 코어 태그
	     - 조건분기나 반복처리 그리고 변수의 지정 등과 같이 논리적인 처리를 위해 사용되는 
	       스크립트 코드를 대체하기 위한 태그 제공
	       
	       1) <c:out>
	        - JSPWriter에 값을 적절하게 처리한 후에 출력
	        
	        속성		타입		설명
	        ----------------------------------------------------------
	        value		Object		출력할값
	        escapesXml	boolean		특수문자를 반활할지의 여부
	        default		Object		value의 결과값이 null인경우 출력할 값(대신 출력됨)
	        
	        
	       2) <c:set>
	        - JSP에서 사용할 변수를 설정
	        
	        속성		타입		설명
	        ----------------------------------------------------------
	        var			String		El 변수 이름
	    	value		Object		변수에 할당할 값
	    	scope		String		변수를 생성할 영역
	    	target		Object		프로퍼티 값을 설정할 객체 지정
	    	property	String		프로퍼티 이름
	    	
	    	
	    	3) <c:remove>
	        - 설정한 변수를 제거한다.
	        
	        속성		타입		설명
	        ----------------------------------------------------------
	    	var			String		삭제할  EL 변수 이름
	    	scope		String		삭제할 변수가 포함된 영역
	    	
	    	
	    	4) <c:catch>
	        - 예외를 처리한다.
	        
	        속성		타입		설명
	        ----------------------------------------------------------
	    	var			String		예외를 저장할 EL 변수 이름
	    	
	    	
	    	5) <c:if>
	        - 조건을 지정하고 지정된 조건과 일치하는 처리 내용을 구현한다.
	        
	        속성		타입		설명
	        ----------------------------------------------------------
	        test		boolean		검사 조건
	        var			String		검사 조건의 계산 결과값을 저장할 EL 변수
	        
	        6) <c:choose>
	        - 여러 조건을 처리할떄 사용
	        
	        
	        7) <c:when>
	        - 여러 조건을 지정하고 지정한 조건과 일치하는 처리 내용을 구현한다. c:choose 요소에서 사용된다.,
	        
	        속성		타입		설명
	        ----------------------------------------------------------
	        test		boolean		검사 조건
	        
	        
	         7) <c:otherwise>
	        - <c:when> 요소에서 지정한 조건에 모두 일치하지 않을떄 처리할 내용을 구현한다,
	        
	        속성		타입		설명
	        ----------------------------------------------------------
	        var			boolean		몸체에서 사용할 EL변수 이름
	        items		String		반복 처리할 데이터
	        varStatus	String		루프 상태를 저장할 EL변수 이름
	        begin		String		시작 인덱스 값
	        end			String		끝 인덱스 값
	        step		String		인덱스 충분 값
	        
	        10) <c:forTokens>
	         - 구분자로 구분된 각각의 토큰을 처리할 떄 사용한다.
	         
	         속성		타입		설명
	        ----------------------------------------------------------
	        var			boolean		몸체에서 사용할 EL변수 이름
	        items		String		반복 처리할 데이터
	        delims		String		구분자
	        varStatus	String		루프 상태를 저장할 EL변수 이름
	        begin		String		시작 인덱스 값
	        end			String		끝 인덱스 값
	        step		String		인덱스 증분 값
	        
	        
	        11) <c:improt>
	         - URL을 사용하여 다른 자원을 삽입 한다.
	         
	         속성		타입		설명
	        ----------------------------------------------------------
	        url			 Stirng		읽어올  URL
	        var			 String		읽어온 결과를 저장할 변수 이름
	        scope		 String		변수를 지정할 영역
	        charEncoding String		결과를 읽어올떄 사용할 캐릭터 인코딩
	        
	        
	        12) <c:url>
	         - URL을 재작성 한다.
	         
	         속성		타입		설명
	        ----------------------------------------------------------
	        value		Stirng		읽어올URL
	        var			String		읽어올 결과를 지정할 변수 이름
	        scope		String		변수를 지정할 영역
	        value 속성값의 두가지 타입
	         - 절대 URL : 완현잔 URL이다.
	         - 상대 URL : 
	         		> 웹 어플리케이션 내에서의 절대 경로 : '/'로 시작하는 경로
	         		> 현재 JSP에 대한 상대 경로 : '/'로 시작하지 않ㄴ,ㄴ다.
	         	웹 어플리케이션 내에서의 절대 경로를 사용할 경우 실제로 생성되는 URL은 컨텍스트 경로를 포함한다.
	         	
	         	
	       	13) <c:redirect>
	         - 지정한 URL에 리다이렉트 한다.
	         
	         속성		타입		설명
	        ----------------------------------------------------------
	        url			 Stirng		리다이렉트할 URL
	        context		Stirng		컨텍스트 경로
	        
	        14) <c:param>
	        
	         속성		타입		설명
	        ----------------------------------------------------------
	        name		String		파라미터 이름
	        value		String		파라미터 값
	         
	 */
	
	// c:out excapeXml 속성의 기본값은 true이다.
	@GetMapping("/home0101")
	public String home0101(Model model) {
		Member member = new Member();
		member.setUserId("<p>홍길동<>&%0101</p>");
		model.addAttribute("member", member);
		return "home/jstl/home0101";
	}
	
	@GetMapping("/home0102")
	public String home0102(Model model) {
		Member member = new Member();
		member.setUserId("<p>홍길동<>&%0101</p>");
		model.addAttribute("member", member);
		return "home/jstl/home0102";
	}
	
	@GetMapping("/home0103")
	public String home0103(Model model) {
		Member member = new Member();
		member.setUserId(null);
		model.addAttribute("member", member);
		return "home/jstl/home0103";
	}
	
	@GetMapping("/home0201")
	public String home0201(Model model) {
		Member member = new Member();
		member.setUserId("asd123");
		model.addAttribute("member", member);
		return "home/jstl/home0201";
	}
	
	
	// JSP 에서 사용될 변수 memberId를 설정하여 사용하고
	// 태그의 몸체를 값으로 사용한다.
	@GetMapping("/home0202")
	public String home0202(Model model) {
		Member member = new Member();
		member.setUserId("asd123");
		model.addAttribute("member", member);
		return "home/jstl/home0202";
	}
	
	// c:remove
	@GetMapping("/home0301")
	public String home0301(Model model) {
		Member member = new Member();
		member.setUserId("asd123");
		model.addAttribute("member", member);
		return "home/jstl/home0301";
	}
	
	// c:catch
	@GetMapping("/home0401")
	public String home0401(Model model) {
		Member member = new Member();
		String[] hArr = {"Music","Movie"};
		member.setHobbyArray(hArr);
		model.addAttribute("member", member);
		return "home/jstl/home0401";
	}
	
	@GetMapping("/home0402")
	public String home0402(Model model) {
		return "home/jstl/home0402";
	}
	
	//c:if
	@GetMapping("/home0501")
	public String home0501(Model model) {
		Member member = new Member();
		member.setForeginer(true);
		model.addAttribute("member",member);
		return "home/jstl/home0501";
	}
	
	// c:when c:otherwise
	@GetMapping("/home0601")
	public String home0601(Model model) {
		Member member = new Member();
		member.setGender("F");
		model.addAttribute("member",member);
		return "home/jstl/home0601";
	}
	
	
	// foreach
	@GetMapping("/home0701")
	public String home0701(Model model) {
		Member member = new Member();
		List<String> list = new ArrayList<String>();
		list.add("음악 듣기");
		list.add("영화 보기");
		list.add("게임 하기");
		list.add("코딩 하기");
		list.add("드라이브 하기");
		member.setHobbyList(list);
		model.addAttribute("member",member);
		return "home/jstl/home0701";
	}
	
	// c:forTokens
	@GetMapping("/home0801")
	public String home0801(Model model) {
		Member member = new Member();
		String hobby = "음악 듣기, 영화 보기, 게임 하기, 코딩 하기";
		member.setHobby(hobby);
		model.addAttribute("member",member);
		return "home/jstl/home0801";
	}
	
	// c:import
	@GetMapping("/home0901")
	public String home0901(Model model) {
		return "home/jstl/home0901";
	}
	
	// c:redirect
	@GetMapping("/home1001")
	public String home1001(Model model) {
		return "home/jstl/home1001";
	}
}






















