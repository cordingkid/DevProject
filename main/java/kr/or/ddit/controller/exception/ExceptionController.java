package kr.or.ddit.controller.exception;

public class ExceptionController {
/*
 	16장 예외처리
 	
 	1. 예외 처리
 	
 		일반적으로 프로그램이 처리되는 동안 특정한 문제가 일어났을떄 처리를 중단하고 다른 처리를 하는것을 예외 처리라고함
 		웹 컨테이너는 기본적으로 예외처리를 하여 기본 에러 페이지를 표시해줌
 		하지만, 페이지 애플리케이션 서버의 내부 정보가 일반 사용자들에서 노출되어 프레임워크의 보안 취약점을 노린공격을 받을수 있음
 		이런점을 고려하여 최대한 사용자가 직접 예외를 처리하여 사용자가 정의한 에러 페이지를 표시하게 해야함
 		
 		1-1) 예외 종류
 		- 스프링 프레임워크 예외
 		- 사용자 정의 예외
 		- 의존 라이브러리에서 발생한 예외
 		- 시스템 예외
 		
 		1-2) 가상 예외 상황
 		- A. 등록할때 재목에 빈값을 입력하여 유효성 검증 예외발생
 		- B. 수정 화면 생성하고 뷰 파일에서 수정 진행할떄 예외발생
 		- C. 삭제할떄 매핑 파일에서 예외 발생
 		- D. 존재하지않는 게시물 조회할떄 사용자가 정의한 예외 발생
 		- E. 존재하지 않는 페이지 URL 요청시 예외발생
 		
 		1-3) 예외처리 불가 기준
 		- 사용자가 정의한 예외 처리기를 거치지 않는 예외로 프레미 워크에서 이러한 예외를 처리한 기본적인 내용은 아래와 같음
 			> HTTP status 400 - bad request
 			
 	2. 예외 생성
 	
 		2-1) 예외 발생 상황
 		
 			- 등록할때 제목에 빈값을 입력하여 유효성 검증 예외 발생
 				> http status 400
 				> [예외 상황 만들 위치]
 					씨알유디 컨트롤러 시알류디레지스터 포스트
 					보드 제목에 @NotBlank 설정
 					
 			- 수정 화면 생성하고 수정 진행 할떄 예외 발생
 				> http status 500
 				> [예외 상황 만들 위치]
 					view/crud/register.jsp id가 title인 엘리먼트 를 title2로변경
 					
 			- 삭제할때 매핑 파일에서 예외발생
 				> http status 500
 				> [예외 상황 만들 위치]
 					sqlmap/boardMapper_SQL.xml id가 delete인 삭제 기능을 가진 태그에서 board_no를 2로ㅓ 변경
 					
 			- 존재 하지않는 게시물을 조회할떄 사용자가 정의한 예외 발생
 				> http staus 500
 				
 			- 존재하지않는 페이지 URL 요청시 예외발생 
 				> http staus 404
 	
 	3. 상태 코드 사용한 에러 페이지 설정
 	- 웹 컨테이너 설정파일(web.xml)의 <error-cod> 요소에 상태코드를 설정하고<location> 요소에 이동 대상 페이지를 지정한다.
 	
 		예외 처리 방법
 		- 웹컨테이너 설정(web.xml)
 			> <error-page></<error-page> 400
 			> <error-page></<error-page> 404
 			> <error-page></<error-page> 500
 		
 		- 처리할수 없는 예외
 			> 존재하지 않는 게시물을 조회할때 사용자가 정의한 예외 발생
 			
 	4. 예외 타입 사용한 에러 페이지 설정
 	- 웹컨테이너 설정 파일(web.xml) <exception.type> 요소에 예외 타입을 설정하고 <lcation> 요소에 이동 대상 페이지를 지정한다.
 	
 		예외 처리 방법
 		- 웹 컨테이너 설정(web.xml)
 			> <error-page>
 			
 		- 처리 할수 있는 예외 
 			500 같은거?
 		
 		-처리 할수 없는 예외 
 			404, 400
 			
 	5. 기본 에러 페이지 설정
 	- 웹컨테이너 설정파일
 			> <error-page>
 			
 		예외 처리 방법
	 		> 웹 컨테이너 설정
	 		> 에러페이지를 jsp파일 절대 경로로 설정
	 		> Controller 에서 선언한 URL 매핑 정보로도 설정 가능
	 		> 서블릿 3.1 이상에서 지원
	 		
	 6. 예외 처리 어노테이션
	 - @ExceptionHandle와 @ControllerAdvice를 이용하여 처리한다.
	 
	 	에외 처리 방법
	 	- @ControllerAdvice 어노테이션은 스프링 컨트롤러에서 예외를 처리하는 핸들러 클래스임을 명시
	 	- @ExceptionHandler 어노테이션은 괄호안에 설정한 예외 타입을 해당 매서드가 처리한다는 것을 의미
	 	
	 	예외 처리 핸들러 생성
	 	- exception 패키지 안에 CommonExceptionHandler 생성
	 	
	 	
	 	- 처리 못하는 예외
	 		> 존재하지 않는게시물일때
	 		
	 7. 예외 정보 출력
	 - 예외에 대한 내용을 Model 객체를 이용해서 전달하여 뷰화면에 출력이 가능
	 
	 예외 처리 방법
	 - COmmonExceptionHandler 클래스에서 에러 정보를 페이지로 던져줌
	 
	 처리 할수 없는 예외
	 
	 8. 404 에러 페이지 처리
	 - web.xml 설정을 통해 처리할수 있다.
	 
	 	예외 처리 방법
	 	- 웹 컨테이너 설정
	 		> 404 에러를 처리할수 있도록 DispatcherServlet의 throwExceptionIfNoHandlerFound 속성을 true로 설정
	 		
	 9. 입력값 검증 예외 처리
	 - @Validated 어노테이션을 사용하면 Bean Validation의 유효성 검증 매커니즘을 이용할수 있다.
	 
	 	예외 처리방법
	 	- 입력값 검증 기능의 활성화와 BindingResult 정의
	 		> 입력값 검증 대상인 자바빈즈 매소드 매개변수에 @Validated 어노테이션을 지정하고 바로 다음에 BindingResult를 정의한다.
	 		BindingResult에는 요청 데이터에 바인딩 에러와 검사 에러 정보가 저장된다.
 */
}
























