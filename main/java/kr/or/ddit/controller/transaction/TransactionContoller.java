package kr.or.ddit.controller.transaction;

public class TransactionContoller {
	/*
	  15장 트랜잭션
	  - 한번에 이루어지는 작업의 단위를 말함
	  
	  		트랠잭션 정의(ACID원칙)
	  		- 원자성(Automicity)
	  			> 하나의 트랜잭션으로 모두 하나의 단위로 처리돠야함
	  		- 일관성
	  			> 트랜잭션이 성동햿다면 그동안에는 외부에서ㅏ 겆정이 없어야한ㄴ다.
	  		- 격리성
	  			> 트랜잭션으로 처리되는 동안에는 외부에서 간섭이 없어야한다.
	  		- 연속성
	  			> 트랜잭션이 성공적으로 처리되면 그 결과는 영속성으로 보관되어야함
	  			
	  	7. 트랜잭션 설정
	  	
	  		2-1) 스프링 설정
	  			- root-context.xml 설정
	  				> 네임스페이스 스키마 추가
	  			- transactionManager 빈등록
	  			- 어노테이션매퍼 기반의 트랜잭션 제어 활성화
	  			
	  	3. 트랜잭션 적용
	  	
	  		[테스트는 AOP가 적용된 CrudBoardController외 연계된 serviceImpl 에서 진행]
	  		
	  		트랜잭션을 사용하는 에 상황이 존재
	  		에) 회원은 반드시 하나의 권한을 가진다는 비즈니스 규칙이 있다.
	  		회원과 회원권한 테입ㄹ으 각각 개별로 존재하지만 회원 정보를 저장할떄 반드시 회원 권한 정보를 동시에 저장해야함
	  		클래스나 매서드에 대해 @Transactional 어노테이션을 부여하는 트랜잭션을 젹용할수 있따
	  		
	  		서비스 구현객체에 트랜잭션을 적용
	  		- 회원정보를 저장하거나 실패하거나 회원 권한 정보를 저장하거나 실패하면 회원 테이블과 회원 권한 테이블을 모두 저장되지 않는다.
	  			회원 정보와 회원 권한 정보 모두 저장에 성공해야 비로소 회원 테이블과 회원 권한 테이블에 저장이 이루어짐
	  			삭제 수정도 마찬가지로 이루어진다.
	  			
	  	@Transactional을 적용하고 중간에 에러를 발생시킨후, 롤백된 상태를 확인하려고 햇으나 롤백이 되지 않음
	  	
	  	그 이유 스프링 프레임워크에서 @Transactional 어노테이션은 기본적으로 Checked Exception에 대해서는 롤백 처리를 하지 않도록 설계되어 있다.
	  	기본적으로 스프링에서 트랜잭션 처리는 RuntimeException 계열이라면 Rollback 처리를 한다.
	  	그러나 UncheckedException 에 대해서는 Rollback 처리를 진행하지 않는다.
	  	
	  	여기서, 트랜잭션으로 국한된 롤백 정책이 아니라 스프링 프레임워크에서의 기본성적에 대한 내용일 뿐이다.
	  	실제로 트랜잭션 롤백처리, checked Exception vs unchecked Exception 등 검색 해보면 잘못된 정보들ㄹ로 복사. 붙여넣기 되어있는 내용이 
	  	수두룩 하여 많은 에러 정보를 공유하고있다.
	  	
	  	***
	  	 '예외 처리시 트랜잭션 처리에 대한정보가 잘못되어 표기된 경우가 많다.'
	  	이럴때 되고 저럴때 되고 아니라 기본적인 스프링 트랜잭션의 정책이 있고 그안에서 정책 이 외에 옵션으로 특정 에러가 발생했을때
	  	롤백을 진행할수 있도록 제공한다
	  	
	  	Exception(예외)와 Error(에러)
	  	- Exception : 개발 알고리즘에서 발생하는 오류로 개발자가 작성한 코드에서 발생하므로 예외를 상황에맞춰 처리 할수 있다ㅓ.
	  	- Error : 시스템에서 발생하는 심각한 수준의 에러로 개발자가 미리 예측하여 대응할 수 없기 때문에 예외 처리에 대한  부분을 신경쓰지 않아도된다.
	  	
	  	CheckedException과 UnCheckedException
	  	- RuntimeExceptiond의 상속 여부에 따라서 Checked Exception UnCecked Exception 으로 나눠진다.
	  	
	  								Checked Exception						UnCecked Exception
	  	---------------------------------------------------------------------------------------------------------------------------
	  	예외처리여부 	반드시 여외 처리 코드가 있어야함					강제로 예외 처리는 아니다.
	  	예외 확인시점	컴파일 단계에서부터 컴파일이 되지않음				런타임중 예외가 확인된다.
	  	클래스			IOException, SQLException...						NullPointException,IndexOutOfBoundException...
	  	
	  	***** 트랜잭션도 AOP의 개념이 반영된 관점 지향 프로그래밍이라 할수 있다.
	  	
	  	3-1) 선언적 트랜잭션(@Transactional)
	  		- 컨트롤러 매소드 각단위로 세밀한 트랜잭션 속성 제어가 가능
	  		- 해당 어노테이션이 클래스 수준에서 선언되면 선언 클래스 및 해당 하위 클래스의 모든 매소드의 기본값으로 적용된다.
	  		- RuntimeExcetion계열, Error 예외에 대해서는 Rollback이 가능하다.
	  		
	  		1) isolation(격리 수준)
	  			- 각 트랜잭션이 존재할떄, 트랜잭션들 끼리 서로 고립된 수준을 나타내며 서로간에 가용된 데이터 컨트롤 할지에 대한 부분들을 설정할수 있다.
	  			- 새롭게 시작된 트랜잭션에만 적용되므로, PropagAtion.REQUIRED 또는 Propagation.REQUIRES_NEW 함계 사용되도록 독점 설계되어있다.
	  			
	  			
	  			
	  			1-1) 옵션
	  			
	  			** 용어
	  			# Dirty read
	  			- commit이 이뤄지지 않은 다른 트랜잭션의 데이터를 읽는것을 의미
	  			# Non-repeatable read(데이터 수정 ,삭제에서)
	  			- 처리중인 트랜잭션에 다른 트랜잭션이 Commit한 데이터를 읽을수 있는 것을 의미
	  			(처음 조회에 대한 트랜잭션과 두번째 조회에 대한 트랜잭션 결과가 다를수 있다,.)
	  			# Phantom read
	  			- 자신이 처리중인 트랜잭션에서 처리했던 내용인에서 다른 트랜잭션이 데이터를 수정후 Commit 하더라도 자신의 트랜잭션에서 처리한 내용만 사용하는 것을 의미
	  			
	  			[Option]
	  			- DEFAULT : 기본 데이터 저장소의 기본 격리 수준을 사용합니다.
	  			- READ_COMMITED : Dirth read가 방지됨을 나타내는 상수
	  							non-repeatable read및 Phantom Read가 발생할수 있다.
	  					> 하나의 트랜잭션 처리가 이루어진 변경 내용이 Commit된후, 다른트랜잭션에서 조회가 ㅡ가능
	  					> A 트랜잭션이 데이터를 변경하고 B 트랜잭션이 조회를 진행할떄 B트랜잭션은 Shard lock이  걸림
	  			- READ_UNCOOITED : Drity read, Non-repeatable Read 및 Phantom Read가 발생할수 있을을 나타낸는상수
	  				> 다른 트랜잭션의 내용이 Commit 또는 Rollbakc 되거나 되지 않아도 다른 트랜잭션에서 조회가 가능합니다.
	  			- RePEATABLE_READ : Drity read, Non-repeatable Read rk 방지됨을 나타내는 상수
	  					> Phantom Read가 발생 가능
	  			-SERIALIZABLE : Drity read, Non-repeatable Read 및 Phantom Read 가 방지됨을 나타내는 상수
	  				> Phantom Read 가 발생하지 않음
	  				> 거의 사용하지 않는 옵션
	  				
	  				
	  	2) propagation (전파 옵션)
	  		- 기존 진행중인 트랜잭션 외에 추가적으로 진행중인 트랜잭션이 존재할때 추가적인 트랜잭션에 대해서 어떻게 처리할지에 대한 설정
	  		- 추가적인 트랜잭션을 기존 트랜잭션에 포함 시켜 함께 처리할 수도 있고, 추가적인 트랜잭션처럼 별도의 트랜잭션으로 추가할수도 있고
	  			다른 트랜잭션처럼 진행되다 에러를 발생 할수도 잇따.
	  	
	  	
	  		2-1) 옵션
	  			
	  			- REQUIRED : 현재 트랜잭션을 지원하고 존재하지 않는 경우 새 트랜잭션을 만듬
	  				> propagation 기본 default 옵션에 해당함
	  				> 부모 자식간에 상관관계에 자식부분이 트랜잭션이 rollvakc 처리시 부모까지 영향이 가서 rollback 처리 된다.
	  			- REQUIRES_NEW : 새로운 트랜잭션 생성
	  				> rollback은 각각 이루어짐
	  			- SUPPORTS : 트랜잭션이 있으면 현재 트랜잭션을 지우너하고 트랜잭션이 없으면 트랜잭션이 아닌 방식으로 실행
	  			- MANDATORY : 현재 트랜잭션을 지원하고, 없으면 예외를 발생시킴
	  			- NESTED : 현재 트랜잭션이 있는 경우 중첩된(부모-자식) 트랜잭션내에서 실행하고 그렇지 않으면 REQUIRED와 같이 동작
	  				> 부모에서 예외가 발생했을떄 자식까지 영향이 가서 commit되지 않느,ㄴ다
	  			- Never : 트랜잭션이 아닌방식으로 실행하고 트랜잭션이 있으면 예외를 발생시킴
	  				> 실행자체가 트랜잭션을 피요하지 않고 트랜잭셔이 존재한다면 예외 발생시카ㅣㅁ
	  				> Exisiting transaction found for transaction marked with propagation 'never' 에러가 발생
	  				
	  				
	  		3) readonly
	  			- 읽기 전용인 경우 설정할수 있는 Bool flag 런타임시 최적하 허용
	  			- 기본값 false
	  			- readonly 속성을 설정했다해서 무조건 설정된다는 보장이 없음
	  				> 읽기전용에 대한 힌트를 분석할수 없는 트랜잭션인 경우 throw를 던지지 않고 조용히 힌트를 무시
	  				
	  		4) timeout (트랜잭션 제한시간)
	  			- 기본값은 -1 로 무제한
	  			- timeout은 클라이언트와 서버와의 통신중, 서버측 문제로 다음 처리를 이어나가지 못하는 Deadloc 을 방지할수 있는 속성
	  			- 클라이언트와 서버간의 RestFul API를 개발시 고려해볼 속성
	  			
	  		5) rollbackFor
	  			- 트랜잭션 롤백을 유발해야 하는 예외 유형을 나타내는 0개의 이상의 옣외 유형을 정의
	  			- 기본저그로 ㅌ,ㅡ랜잭션은 롤백 되지만 런타임 익셉션 개열과 에러 는 롤백 안됨
	  			- 깁ㄴ적인 정책 이외에 에러를 처리할 경우 해당 에러러ㅡ선언하여 롤백 청책을 추가하낟,.
	  			
	  			
	  	
	  	
	 */
}



















