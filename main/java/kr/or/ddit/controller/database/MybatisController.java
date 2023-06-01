package kr.or.ddit.controller.database;

public class MybatisController {
	/*
	 	12장 마이바티스
	 	
	 	1. 마이바티스란?
	 	
	 		1) 뭔데?
	 		마이바티스는 자바 퍼시스턴스 프레임워크의 하나로 XML 서술자나 어노테이션을 사용하여 저장프로시저나 SQL문으로 객체들을 연결시킨다.
	 		마이바티스는 Apache 라이선스 2.0d으로 배포되는 자유 소프트웨어입니다.
	 		
	 		2) 마이바티스를 사용함으로써 얻을 수 있는 이점
	 		- SQL의 세계적인 관리
	 		- 자바 객체와 SQL 입출력 값이 투명한 바인딩
	 		- 동적 SQL 조합
	 		
	 		3) 마이바티스 설정
	 			3-1) 의존 관계 정의
	 			- 총 6가지 라이브러리를 등록하여 관계를 정의함 (DatabaseConnectController 참고)
	 			3-2) 스프링과 마이바티스 연결 설정
	 			- root-context.xml 설정 (DatabaseConnectController 참고)
	 			- 총 3가지를 등록하여 설정 (추가로 Mapper를 등록하기 위한 basePackge 정보도 함께 추가합니다)
	 			3-3) 마이바티스 설정
	 			- WEB-INF/mybatisAlias/mybatisAlias.xml 설정
	 			- 마이바티스의 위치 설정은 root-context.xml 의 sqlSessionFactory 설정시 property 요소로 적용
	 			
	 		4) 관련 테이블 생성
	 			4-1) board 테이블 생성
	 			4-2) member 테이블 생성
	 			4-3) member_auth 테이블 생성
	 			
	 	2. Mapper 인터페이스
	 	- 인터페이스의 구현을 mybatis-spring에서 자동으로 생성할 수 있다.
	 	
	 		1) 마이바티스 구현
	 		
	 			1-1) Mapper 인터페이스
	 			- BoardMapper.java 생성(인터페이스)
	 			
	 			1-2) Mapper 인터페이스와 매핑할 Mapper
	 			- sqlmap/boardMapper_SQL.xml 생성
	 			
	 			1-3) 게시판 구현 설명
	 			- 게시판 컨트롤러 만들기 (board/CrudBoardController)
	 			- 게시판 등록 화면 컨트롤러 메소드 만들기 (crudRegister:get)
	 			- 게시판 등록화면 만들기(crud/register.jsp)
	 			- 게시판 등록 기능 컨트롤러 메소드 만들기(crudRegister:post)
	 			- 게시판 등록 기능 서비스 인터페이스 메소드 만들기
	 			- 게시판 등록 기능 서비스 클래스 매소드 만들기
	 			- 게시판 등록 기능 Mapper 인터페이스 메소드 만들기
	 			- 게시판 등록 기능 mapper xml 쿼리 만들기
	 			- 게시판 등록 완료 페이지 만들기
	 			- 여기까지 확인
	 			
	 			- 게시판 목록 화면 컨트롤러 메소드 만들기
	 			- 게시판 목록화면 서비스 인터페이스 메소드 만들기
	 			- 게시판 목록 화면 서비스 클래스 매소드 만들기
	 			- 게시판 목록 화면 Mapper 인터페이스 메소드 만들기
	 			- 게시판 목록 화면 Mapper 인터페이스 메소드 만들기
	 			- 게시판 목록 화면 MApper xml 쿼리 만들기
	 			- 게시판 목록 화면 만들기
	 			- 여기까지
	 			
	 			- 게시판 상세 화면 컨트롤러 메소드 만들기
	 			- 게시판 상세 화면 서비스 인터페이스 메소드 만들기
	 			- 게시판 상세 화면 서비스 클레스 매소드 만들기
	 			- 게시판 상세 화면 Mapper 하기
	 			- 게시판 상세 화면 만들기
	 			
	 			
	 			
	 	3. 별칭 적용
	 	- TypeAlias로 매핑 파일에서 반복적으로 사용될 패키지의 이름을 정의 한다.
	 	
	 		1) 마이바티스 설정
	 		
	 			1-1) mybatisAlias.xml 설정
	 				- typeAlias 설정을 한다.
	 				
	 			1-2) boardMapper_SQL.xml 의 type의 설정을 별칭으로 설정
	 				- mybatisAlias가 설정 되어 있지 않는 경우엔 타입으로 설정하고자 하는 타입 형태를 패키지 명이 포함되어있는 구조로 설정해야 한다.
	 				- 퀴리 태그에 각각 셋팅한 패키지명 대신 alias로 설정한 별칭으로 대체
	 				
	 	4. '_'로 구분된 컬럼명 자동 매핑
	 	- 마이바티스 설정의 mapUnderscoreToCamelCase 프로퍼티 값을 true로 지정하면 _ 구분된 컬럼명을 낙타로 바꿔줌
	 		프로퍼미영으로 자동 매핑 가능
	 		
	 		1) 마이바티스 설정
	 		
	 			1-1) mybatisAlias.xml 설정
	 				- <settings>
	 				-	<setting name="mapUnderscoreToCamelCase" value="true"/> 설정 추가
	 				- </settings>
	 				
	 			1-2) 매핑 파일 수정
	 			- as boardNo, as regDate로 컬럼명을 대체하는 알리아스가 설정되어 있는 경우엔 as 부분을 삭제
	 	
	 	5. 기본키 취득
	 	- 마이바티스는 useGeneratedKeys 속성을 이용하여 insert할 때 데이터 베이스 측에서 채변된 기본키를 취득 가능
	 	
	 		1) 마이바티스 설정
	 			
	 			1-1) 매핑 파일 수정
	 			- create 부분의 속성 추가
	 				> useGeneratedKeys="true" keyProperty="boardNo" 속성을 추가로 사용
	 				> <selectKey keyProperty="boardNo" resultType="int" order="BEFORE">
					>	select seq_board.nextval from dual
					>  </selectKey>
					
					아래 인서트 쿼리가 실행되기 전 실행되서 boardNo를 생성
					그걸 가지고 인서트 하게됨
					객체에 담겨서 컨트롤러까지 감
					
					*** seq_board.nextval 대신 현재의 seq변호를 가져오기 위해 currval를 사용하게 되는 경우가 있다,.
						이때 사용시 주의 사항이 있음
						
						- select seq_board.currval from dual
						> 위 select 쿼리를 사용시 currval 를 사용하는데 있어서 사용 분기에 대한에러가 발생가능
							currval를 사용할 때는 select seq_board.nextval from dual로 먼저 최초 실행후,
							select seq_board.currval from dual로 사용하면 에러 없음
							
						# 그럼에도 나는 가져와야 겠다 다 필요없고 걍 내놔 하고 싶으면
						> select last_number from user_sequences where sequence_name = 시퀀스명
						으로 해라 난 안할거임 ㅇㅇ
						
				2-2) 컨트롤러 메소드에서 crudRegister부분의 등록이 되고 난후 리턴하는 방향성을 수정
				- 지금은 완료페이지로 전환되지만 board에 담겨있는 boardNo를 이용하여 상세 보기 페이지로 전환해도 무방
				
		6. 동적 SQL
		- 마이바티스는 동적 SQL을 조립하는 구조를 지원 SQL조립 규칙을 매핑 파일에 정의 가능
		
			1) 동적으로 SQL 조립하기 위한 SQL 요소
			- <where>
			> where 절 앞뒤에 내용을 더 추가 하거나 삭제 할떄 사용
			- <if>
			> 조건을 만족할때만 가능
			- <choose>
			> 여러 선택중 하나 고를떄
			
			
			
		7. 일대다 관계 테이블 매핑
		- 마이바티스 기능을 활용하여 매핑 파일을 적절하게 정의하면 일대다 관계 테이블 매핑을 쉽게 처리가능
	 			
	 			1) 게시판 구현 설명
	 			
	 				- 회원 등록 화면 컨트롤러 만들기(member/CrudMemberController)
	 				- 회원 등록화면 컨트롤러 메소드 만들기
	 				- 회원 등록 화면 만들기
	 				
	 */
}

































