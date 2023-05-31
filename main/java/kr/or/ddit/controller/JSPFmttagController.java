package kr.or.ddit.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/fmttag")
@Slf4j
public class JSPFmttagController {
	
	/*
	    7. 숫자 및 날짜 포맷팅 처리 태그
	     - 숫자 및 날짜의 포맷팅과 관련된 태그이다.
	     - 문자열을 숫자로, 문자열을 날짜로 변형하여 사용가능
	     
	     		1) <fmt:formatNumber>
	     		 - 숫자를 형식화 한다.
	     		 
	     		 속성			타입				설명
	     		 --------------------------------------------------------------------
	     		 value			String of Number	서식에 맞춰 출력할 숫자
	     		 type			String				어떤 서식으로 출력할지를 정한다.
	     		 pattern		String				직접 숫자를 출력할 서식을 지정한다.
	     		 var			String				포맷팅한 결과를 지정할 변수 이름
	     		 
	     		 ** type 속성 : number 일 경우 숫자 형식으로, parcent일 경우 % 형식으로, currency일 경우
	     		 통화 형식으로 출력한다.
	     		 기본값은 number이다 currency는 속해 있는 국가 코드에 통화 형식이 부여된다.
	     		 
	     		 2) <fmt:parseNumber>
	     		 - 문자열을 숫자로 변환
	     		 
	     		 속성			타입				설명
	     		 --------------------------------------------------------------------
	     		 value			String				파싱할 문자열
	     		 type			String				value 속성의 문자열 타입을 지정
	     		 pattern		String				파싱할 때 직접 사용할 서식을 지정
	     		 var			String				파싱한 결과를 지정할 변수 이름을 지정
	     		 
	     		 ** type 속성 : number, currency, parcent가 올 수 있다.
	     		 
	     		 3) <fmt:formatDate>
	     		 - Date객체를 문자열로 변환
	     		 
	     		 속성			타입				설명
	     		 --------------------------------------------------------------------
	     		 value			java.util.Date		포맷팅한 날짜 및 시간 값
	     		 type			String				날짜, 시간 또는 둘다 포맷팅 할지의 여부 지정
	     		 dateStyle		String				날짜에 대해 미리 정의된 포맷팅 스타일 지정
	     		 timstyle		String				시간에 대해 미리 정의된 포맷팅 스타일을 지정한다.
	     		 pattrern		String				파싱할떄 직접 사용할 서식을 지정
	     		 var			String				파싱한 결과를 저장할 변수 이름을 지정
	     		 
	     		 ** type 속성 : time, date, both 중 한가지 값을 가질수 있으며 기본값은 date이다.
	     		 dateStyle 속성: desfault, short, medium, long, full 중 한가지 값을 가질수 있으며, 기본값은 디펄트 이다.
	     		 timeStyle 속성: desfault, short, medium, long, full 중 한가지 값을 가질수 있으며, 기본값은 디펄트 이다.
	     		 
	     		 
	     		 4) <fmt:parseDate>
	     		 - 문자열을 Date 객체로 변환
	     		 
	     		 속성			타입				설명
	     		 --------------------------------------------------------------------
	     		 value			String				파싱할 문자열
	     		 type			String				날짜 , 시간 또는 둘다 포맷팅 할지의 여부를 지정
	     		 dateStyle		String				날짜에 대해 미리 정의된 포맷팅 스타일을 지정
	     		 timeStyle		String				시간에 대해 미리 정의된 포맷팅 스타일 지정
	     		 pattrern		String				파싱할떄 직접 사용할 서식을 지정
	     		 var			String				파싱한 결과를 저장할 변수 이름을 지정
	     		 
	     		 ** type 속성 : time, date, both 중 한가지 값을 가질수 있으며 기본값은 date이다.
	     		 dateStyle 속성: desfault, short, medium, long, full 중 한가지 값을 가질수 있으며, 기본값은 디펄트 이다.
	     		 timeStyle 속성: desfault, short, medium, long, full 중 한가지 값을 가질수 있으며, 기본값은 디펄트 이다.
	     		 lika 이고르?
	 */
	
	// 1) type 속성을 지정하거나 pattern 속성을 지정하여 숫자를 형식화 한다.
	@GetMapping("/home0101")
	public String home0101(Model model) {
		int coin = 100;
		model.addAttribute("coin",coin);
		return "home/fmttag/home0101";
	}
	
	
	// 2) type 속성이 지정되지 않으면 기본값은 number이다.
	@GetMapping("/home0201")
	public String home0201(Model model) {
		int coin = 1000;
		model.addAttribute("coin",coin);
		return "home/fmttag/home0201";
	}
	
	// 3) type 속성이 currency인 경우
	// 만약 type 속성이 percent인 경우엔 넘겨받아야할 값이 '1000%' 과 같이 % 의 스타일 형태 값이 넘어가야 한다.
	// 그래서 type 속성과 일치하여 값을 파싱할 수 있다.
	@GetMapping("/home0202")
	public String home0202(Model model) {
		String coin = "￦1000";
		model.addAttribute("coin",coin);
		return "home/fmttag/home0202";
	}
	
	// pattern 속성을 사용하여 직접 사용할 서식을 지정한다.
	@GetMapping("/home0204")
	public String home0204(Model model) {
		String coin = "1,000.25";
		model.addAttribute("coin",coin);
		return "home/fmttag/home0204";
	}
	
	// 6) type 속성을 date로 지정하여 날짜 포맷팅을 한다.
	@GetMapping("/home0301")
	public String home0301(Model model) {
		Date date = new Date();
		model.addAttribute("now",date);
		return "home/fmttag/home0301";
	}
	
	// 7) type 속성을 time으로 지정해야 시간 포맷팅을 한다.
	@GetMapping("/home0302")
	public String home0302(Model model) {
		Date date = new Date();
		model.addAttribute("now", date);
		return "home/fmttag/home0302";
	}
	
	// 8) type 속성을 both로 지정하여 날짜, 시간 둘다 포맷팅 한다.
	@GetMapping("/home0303")
	public String home0303(Model model) {
		Date date = new Date();
		model.addAttribute("now", date);
		return "home/fmttag/home0303";
	}
	
	// 9) pattern 속성을 지정하여 날짜를 포맷팅 한다.
	@GetMapping("/home0304")
	public String home0304(Model model) {
		Date date = new Date();
		model.addAttribute("now", date);
		return "home/fmttag/home0304";
	}
	
	// dateStyle 속성을 지정하지 않으면 기본값은 default이다.
	@GetMapping("/home0401")
	public String home0401(Model model) {
		String dateValue = "2020. 10. 20";
		model.addAttribute("dateValue", dateValue);
		return "home/fmttag/home0401";
	}
	
	// dateStyle 속성을 short로 지정하여 문자열을 Date객체로 변환한다.
	@GetMapping("/home0402")
	public String home0402(Model model) {
		String dateValue = "20. 2. 1";
//		String dateValue = "2020. 2. 1";  // 미디움
//		String dateValue = "2020년 2월 1일 (금)"; // 롱 형태
//		String dateValue = "2020년 2월 1일 금요일";  //풀 형태
		model.addAttribute("dateValue", dateValue);
		return "home/fmttag/home0402";
	}
	
	// 12) pattern 속성을 지정하여 문자열을 Date 객체로 변환
	@GetMapping("/home0403")
	public String home0403(Model model) {
		String dateValue = "2020-02-01 15:00:23";	// pattern 지정
		model.addAttribute("dateValue", dateValue);
		return "home/fmttag/home0403";
	}
}






















