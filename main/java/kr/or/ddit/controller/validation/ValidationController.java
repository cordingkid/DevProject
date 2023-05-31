package kr.or.ddit.controller.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/validation")
@Slf4j
public class ValidationController {
	/*
	 	9장 입력 유효성 검증
	 	
	 		1. 입력값 검증
	 		 - 스프링 MVC Bean Validation 기능을 이용해 요청 파라미터 값이 바인딩된 도메인 클래스의 입력값 검증을 한다.
	 		 
	 		[환경설정]	 
	 		# 의존 관계 정의
	 		 - 입력값 검증을 위한 의존 라이브러리를 추가
	 		 - pom.xml 에서 hibernate-validator 추가
	 		 
	 		# 입력값 검증 활성화
	 		 - Member 클래스로 넘어가서 임시 테스트로 userId, userName에 규칙을 활성화 한다.
	 		 - 이때, 규칙을 활성화 하기 위해서 사용할 어노테이션이 있다.
	 		 	> @Validated를 지정
	 		 	> 입력값 검증 내장의 도메인 클래스 직후에 BindingResult를 정의 한다.
	 		 		> BindingResult에는 요청 데이터의 바인딩 오류와 입력값 검증 오류 정보가 저장된다.
	 		 		
	 		 # 입력값 검증 환경설정 순서
	 		 1. 입력값 검증을 위한 의존 라이브러리 추가
	 		 2. 입력값 검증 활성화
	 		 	> 활성화를 할 도메인 클래스인데 @Validated 어노테이션을 지정한다.
	 		 3. 도메인 클래스 내 필드에다가 검증을 위한 어노테이션들로 데이터 검증을 설정한다(@NotBlank, @Size 등등)
	 		 4. 에러를 받을 BindingResult를 설정한다. (컨트롤러 매서드 내에 설정합니다)
	 */
	
	@RequestMapping(value = "/registerValidationForm01", method = RequestMethod.GET)
	public String registerValidationForm01(Model model) {
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm01";
	}
	
	@RequestMapping(value = "/result", method = RequestMethod.POST)
	public String registerValidationResult(@Validated Member member, BindingResult result) {
		log.info("member.getUserId() : "+member.getUserId());
		log.info("member.getUserName() : "+member.getUserName());
		log.info("member.getPassword() : "+member.getPassword());
		log.info("member.getGender() : "+member.getGender());
		log.info("member.getEmail() : "+member.getEmail());
		if(result.hasErrors()) {
			return "validation/registerValidationForm01";
		}
		return "validation/success";
	}
	
	/*
	 	2. 입력값 검증 결과
	 	 - 입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의 한다.
	 	 
	 	 	# BindingResult에는 요청 데이터의 바인딩 에러와 입력값 검증 에러 정보가 저장된다.
	 	 	
	 	 	1) 에러 정보 확인을 위한 BindingResult aptjem
	 	 	
	 	 		hasErrors()
	 	 		 - 에러가 발생한 경우 true를 반환한다.
	 	 		hasGlobalErrors()
	 	 		- 객체 레벨의 에러가 발생한 경우 true를 반환한다.
	 	 		haseFieldErrors()
	 	 		- 필드 레벨의 에러가 발생할 경우 트루 반환
	 	 		haseFieldErrors(String)
	 	 		- 인수에 지정한 필드에서 에러가 발생할 경우 true 반환
	 */
	
	@GetMapping("/registerValidationForm02")
	public String registerValidationForm02(Model model) {
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm02";
	}
	
	@RequestMapping(value = "/result2", method = RequestMethod.POST)
	public String registerValidationResult2(@Validated Member member, BindingResult result) {
		log.info("registerValidationResult2() 실행......");
		log.info("result.hasError() : " + result.hasErrors());
		
		if(result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			List<ObjectError> globalErrors = result.getGlobalErrors();
			List<FieldError> fieldErrors = result.getFieldErrors();
			
			log.info("allErrors.size() : "+ allErrors.size());
			log.info("globalErrors.size() : "+ globalErrors.size());
			log.info("fieldErrors.size() : "+ fieldErrors.size());
			
			// 객체와 필드 레벨의 에러정보 모두를 출력
			for (ObjectError error : allErrors) {
				log.info("allErrors : " + error);
			}
			
			// 객체 레벨의 에러정보를 출력
			for (ObjectError error : globalErrors) {
				log.info("globalErrors : " + error);
			}
			
			// 필드 레벨의 에러 정보를 출력
			for (FieldError error : fieldErrors) {
				log.info("fieldErrors : " + error);
				log.info("fieldErrors.getDefaultMessage() : " + error.getDefaultMessage());
			}
			return "validation/registerValidationForm02";
		}
		
		log.info("member.getUserId() : "+member.getUserId());
		log.info("member.getUserName() : "+member.getUserName());
		log.info("member.getPassword() : "+member.getPassword());
		log.info("member.getGender() : "+member.getGender());
		log.info("member.getEmail() : "+member.getEmail());
		
		return "validation/success";
	}
	
	/*
	 	3. 입력값 검증 규칙
	 	- 입력값 검증 규칙은 Bean Validation이 제공하는 제약 어노테이션으로 설정한다.
	 	
	 		검사 규칙은 크게 다음 3가지로 분류
	 		- Bean Validation 표준 제약 어노테이션
	 		- 서드파티에서 구현한 제약 어노테이션(서드파티란 제 3자에서 받는)
	 		- 직접 구현한 제약 어노테이션
	 		
	 		1) Member 클래스에서 테스트를 위한 어노테이션으로 설정
	 		@NotNull			:빈값이 아닌지 검사
	 		@Null				: null 인지 검사
	 		@NotBlank			: 문자열이 null이 아니고 trim한 길이가 0보다 크다는것을 검사
	 		@NotEmpty			: 문자열이 null이거나 비어있는지 검사
	 		@Size				: 글자 수나 컬렉션의 요소 개수를 검사
	 			>@Size(max -, min = )
	 		@Max(value=)		: value보다 작거나 같은걸 검사
	 		@Min(value=)		: value보다 크거나 같은걸 검사
	 		@Email				: email 주소 형식인지를 검사
	 		@Past				: 과거 날짜인지 검사
	 		@Future				: 미래 날짜 인지 검사
	 		@Pattern(regexp=)	: CharSequence는 지정된 정규식과 일치해야하고, 정규식은 Java 정규식 규칙을 따른다.
	 		@Positive			: 양수여야 합니다(0은 에러)
	 		@Legnth(min, max)	: 문자열의 길이가 min 과 max 사이인지 확인
	 		
	 		[테스트]
	 		- Member 클래스의 검증활성화 추가
	 			> userId, password, userName, email, dateOfBirth
	 		- 테스트 URL : /validation/registerValidationForm02로 요청해서 테스트를 진행하기 
	 		
	 		
	 	4. 중첩된 자바빈즈 입력값 검증
	 	- 중첨된 자바빈즈와 자바빈즈의컬렉션에서 정의한 프로퍼티에 대해 입력값 검증을 할떄는 @Valid를 지정한다.
	 	
	 		1) 중첩된 자바빈즈 클래스를 정의하고 @Valid를 지정한다.
	 		- Member 클래스 내 Address address 필드에 @Valid 어노테이션을 지정
	 		- Member 클래스 내 List<Card> cardList 필드에 @Valid 어노테이션을 지정
	 		
	 		2) Address 클래스에도 validation을 설정한다.
	 		3) Card 클래스에도 validation을 설정한다.
	 */
	
	@GetMapping("/registerValidationForm03")
	public String registerValidationForm03(Model model) {
		log.info("registerValidationForm03() 실행 ....!!");
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm03";
	}
	
	@PostMapping("result3")
	public String registerValidationResult3(@Validated Member member, BindingResult result) {
		log.info("registerValidationResult3() 실행...!");
		
		if(result.hasErrors()) {
			return "validation/registerValidationForm03";
		}
		return "validation/success";
	}
}
























