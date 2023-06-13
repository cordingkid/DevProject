package kr.or.ddit.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/* 
 	@Component 는 스프링 빈으로 등록하기 위한 어노테이션
	이름을 명시 하지 않아도 클래스 이름을 따서 만들어준다 그러나 정확하게 하기위해 이름 명시 해줌
	@Aspect는 어노테이션을 붙여 이 클래스가 Aspect를 나타내는 클래스 란걸 명시
	AOP Controller 클래스 빈 등록시 aopController로할지 aoPController로 할지가 명확하지 않을수 있어서 명시해줌
*/
@Component("aopController")
@Aspect
@Slf4j
public class AOPController {
	
	/*
	 	14장 AOP
	 	
	 		[AOP란? 예시]
	 		406호 반장 정수학생이 신입으로 프로젝트를 진행하고 있다.
	 		그러던 어느날, 성희 팀장님을 통해 지금 개발중인 서비스 처리속도좀 로그로 남겨주세요 라는 부탁을 받았다
	 		반장 정수학생은 부탁받은 요구사항을 이행하기위해 본인이 만들고 있는 서비스 로직에서 처리속도를 찍어볼 메소드를 개발해 처리 속도가 잘찍히는걸확인한다.
	 		기분이 좋은 정수학생은 팀장님께 달려감 
	 		성희 팀장님은 이후 긍정적인 검토안을 정수학생 한테 전달하면서 전체 각 처리속도를 찍어줘라고햇음
	 		
	 			* 서비스 로직에서 제일 중요한 로직은 본래의 기능이 제일 중요하고 지금 내가 작성하는 로직은 옵션이 추가되는ㄱ게 아닐라나?
	 			* 그럼 이걸 하나의 묶음으로는 처리가 불가능한건가?
	 		하는 생각이 듬
	 		
	 		여기서 시간을 측정하고 권한을 체크하는등의 기능은 옵션과 같은부가기능으로 일종의 인프라로직이라고 하는데 이 인프라 로직은 애플리케이션 전 영역에서
	 		나타날 수 있고 중복코드를 만들어 내 개발의 효율성을 저하시키고 유지보수가 힘들어 질수 있다.
	 		
	 		이러한 인프라 로직은 아래처럼 하나의 관심사를 가질수 있는데 이런 관심사들이 중복이 횡단으로 나타나는데 이것을 가르켜 횡단 관심사(Cross-Cutting- Concern) 라고 함
	 		
	 		┌───────────────────────────────────────────────────────────────────────┐
	 		│ 처리속도측정  		처리속도측정		처리속도츨정	처리속도측정│
	 		└───────────────────────────────────────────────────────────────────────┘
	 			비즈니스로직		비즈니스로직		비즈니스로직		비즈니스로직
	 			처리내용로깅		처리내용로깅		처리내용로깅		처리내용로깅
	 			
	 		이런 횡단관심사를 통해서 프로그래밍하는것이 AOP라고 한다,.
	 		
	 		** 간단하게 맛보기
	 			
	 			- Aspect : AOP의 단위가 되는 횡단 관심사
	 			- 횡단 관심사 : 핵심 비즈니스 로직(매소드 실행시작시간출력, 매소드 처리후 시간출력등)과 다소 거리가 있지만
	 							여러 모듈에서 공통적이고 반복적인 처리를 요구하는 내용(매소드 실행시작시간출력등)
	 							
	 			- 횡단 관심사 분리 : 횡단 관심사에 해당하는 부분 을 분리해서 한곳으로 모으는것을 의미
	 			
	 			- @Component : @Aspect와 짝꿍 component-scan시 여기 있다 라는 의미
	 			- JoinPoint : 어드바이스가 적용될수 있는 위치
	 			- Advice : 어떤 부가기능을 언제 사용할지 정의
	 				* 언제?
	 				> Before : 조인포인트 전에 실행
	 				> After : 조인 포인트 처리가 완료된후 실행
	 				> After Returning : 조인포인트가 정상적으로 종료후 실행
	 				> After Throwing : 조인포인트에서 예외 발생 시 실행 발생안되면 실행 안함
	 				> Around : 조인 포인트 전후에 실행
	 			
	 			
	 			
	 	AOP(관점 지향 프로그래밍 (Aspect Oriented Programming))
	 	- 관점 지향 프로그래밍을 의미하는 약자
	 	
	 		1-1) 관점 지향 프로그래밍
	 		소크 코드의 여기저기에 흩어져 있는 횡단 관심사를 중심으로 설계와 구현을 하는 프로그래밍 기법이다.
	 		간단하게 설명하면 관점 지향 프로그래밍은 횡단 관심사의 분리르 실현하는 방법이다.
	 		
	 		- 횡단 관심사
	 		> 핵심 비즈니스 로직과 다소 거리가 있지만 여러 모듈에서 공통적이고 반복적인 처리를 요구하는 내용이다.
	 		
	 		- 횡단 관심사의 분리
	 		> 어플리케이션을 개발할때 횡단 관심사에 해당하는 부분을 분리해서 한곳으로 모으는 것을 의미한다.
	 		
	 		1-2) AOP 개발 순서
	 		- (1) 핵심 비즈니스 로직에만 근거해서 코드를 작성한다.
	 		- (2) 주변로젝에 해당하는 관심사들을 분맇서 따로 작성한다.
	 		- (3) 핵심 비즈니스 로직 대상 객체에 어떤 관심사들을 결합할 것인지 설정한다.
	 		
	 		1-3) AOP 사용예(로 보 트 에)
	 		- 로깅
	 		- 보안적용
	 		- 트랜잭션 관리
	 		- 예외처리
	 		
	 			** AOP의 사용된예에서 우리는 로깅을 이용한다.
	 				기본적인 보안적용은 스프링 시큐리트에서 사용하고, 트랜잭션 관리는 @Transactional 어노테이션을 활용한 트랜잭션에서 사용하고,
	 				예외처리는 xml설정을 통한 예외 처리에서 사용하도록 한다.
	 				
	 				
	 				
	 		1-4) AOP 관련용어
	 			
	 			- 어스팩트
	 				> AOP의 단위가 되는 횡단 관심사에 해당
	 			- 조인 포인트
	 				> 횡단 관심사가 실행될 시점이나 지점을 말함
	 				> 어디에 적용할 것인지 결정 매소드? 객체 생성시? 필드접근시?
	 			- 어드바이스
	 				> 특정 조인 포인트에서 실행되는 코드로 횡단 관심사를 실제로 구현해서 처리하는 부분
	 				> 어떤 부가 기능을 구현할 것인지 결정
	 			- 포인트컷
	 				> 수만읜 조인 포인트 중에서 실제로 어드바이스를 적용할 곳을 선별하기 위한 표현식을 맣함
	 				> 어드바이스가 적용될 시점'
	 			- 위빙
	 				> 어플리케이션 코드이 적절한 지점에 Aspect를 저용하는것을 말함
	 			- 타겟
	 				>AOP 처리에 의해 처리 흐름에 변화가 생긴 객체를 말함
	 				> 어떤 대상에 대해서 부가 기능을 설정한 것인지 결정
	 				
	 		1-5) 스프링 지원 어드바이스 유형
	 			
	 			- before
	 				> 조인포인트 전에 실행
	 				> 예외가 발생하는 경우만 제외하고 항상 실행
	 			- after returning
	 				> 조인포인트가 정상적으로 종료한 후에 실행
	 			- after throwing
	 				> 조인포인트에서 예외가 발생할떄 실행
	 			- after
	 				> 조인포인트에서 처리가 완료된 후에 실행
	 			- around
	 				> 조인 포인트 전후에 실행된다.
	 		
	 		1-6) AOP의 기능을 활용하기 위한 설정
	 		
	 			(1) 의존 관계 등록
	 			- aspectjrt
	 			- aspectWeaver
	 			
	 			(2) 스프링 AOP 설정
	 			- root-context.xml 설정
	 			: 이곳에서는 AOP를 활성화 하기 위한 태그를 작성
	 	
	 	2. 포인트 컷 표현식
	 	- excution 지시작에 대해서 알아본다.
	 		
	 		2-1) execution 지시자의 표현 방식
	 		- execution 지시자를 활용해 포인트 컷을 표현할 것이다.
	 		
	 			포인트컷 표현 요소
	 			
	 				예) execution(Board.kr.or.ddit.service.IBoardService.BoardService.read(...))
	 				
	 				표현요소				|			설명
	 			----------------------------------------------------------------------------
	 				execution						지시자
	 				Board							반환값
	 				kr.or.ddit.service				패키지
	 				BoardService					클래스
	 				read*							매소드
	 				(...)							변수, 파라미터
	 				
	 		2-2) 포인트컷 표현식에 사용하는 와일드카드
	 		
	 				와일드 카드				|		설명
	 			----------------------------------------------------------------------------
	 				*							임의의 패키지 1개 계층을 의미하거나 임의의 인수 1개를 의미
	 				..							패키지 0개이상 계층을 의미하거나 임의의 인수 0개 이상을 의미
	 				+							클래스명 두,ㅣ에 붙여 쓰며 해당클래스와 해당클래스의 서브 클래스 혹은 구현클래스 모두를 의미
	 				
	 		2-3) 포인트컷 표현식을 적용할 모습
	 		
	 			@Before("execution(* kr.or.ddit.service.IBoardService.BoardService*.*(...))
	 			public void startLog(JoinPoint jp){
	 				log.info("startLog : " + jp.getSigature());
	 			}
	 			
	 	3. Before 어드바이스
	 	- 조인 포인트 전에 실행
	 	
	 		AOP는 서비스내의 비즈니스 로직을 가로채서 컨트롤 하는데 이떄 프록시 라는 녀석을 이용해 해당 기능을 실행하기 전후 로 나눠
	 		로그를 출력하는 등의 이벤트를 발생한다.
	 		
	 		[AOP 프록시]
	 		클라이언특가 요청한 해당 요청을 서비스로직으로 전달되기 전 해당 비즈니스 로직 부를 실체 대상인것처럼 위장해서 클라이언트의 요청을 받음
	 		AOP 에서 프록시 개념은 대리자라고 생각하셈
	 		우리는 서비스에서 전역의 비즈니스 로직들에서 관심사별로 위빙을 지정에 해당 서비스의 비즈니스 로직이 실행될때,
	 		인터페이스 기반의 프록시를 이용하여 설정된 횡단 관심사들을 실행한다.
	 		
	 			*** 인터페이스 기반의 프록시가 잘 설정되어 있는지 확인하기 위해서 CrudBoardController에서
	 				프록시 여부를 확인하자
	 */
	
	@Before("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("[@Before] startLog");
		// getSignature() : 어떤 클래스의 어떤 매소드가 실행되었는지를 보여줌 파라미터타입은 무엇인지 보여줌
		
		log.info("[@Before] startLog : " + jp.getSignature());
		// getArgs() 전달된 파라미터의 정보를 보여준다
		log.info("[@Before] startLog :" + Arrays.toString(jp.getArgs()));
		
		// 8. 매서드 정보 획득시 사용
		// 프록시가 입혀지기 전의 원본대상 객체를 가져옴
		Object targetObject = jp.getTarget();
		log.info("targetObject : "+targetObject);
		
		
		// 프록시 가져옴
		Object thisObj = jp.getThis();
		log.info("thisObj :" + thisObj);
		
		// 인수를 가져옴
		Object args = jp.getArgs();
		log.info("args :" + args);
	}
	// 재균님 안녕하세요
	// 누구냐?
	
	/*
	 	4. After Returning 어드바이스
	 	- 조인 포인트가 정상적으로 종료한 후에 실행 예외 발생하면 노실행
	 */
	@AfterReturning("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void logReturning(JoinPoint jp) {
		log.info("[@AfterReturning] logReturning");
		log.info("[@AfterReturning] logReturning : " + jp.getSignature());
	}
	
	/*
	 	5. AfterThrowing 어드바이스
	 	- 조인 포인트에서 예외가 발생했을떄 실행된다. 예외가 발생하지 않고 정상적으로 종료되면 실행되지않는다.
	 */
	
	@AfterThrowing(pointcut = "execution(* kr.or.ddit.service.IBoardService.*(..))", throwing = "e")
	public void logException(JoinPoint jp, Exception e) {
		log.info("[@AfterThrowing] logException");
		log.info("[@AfterThrowing] logException : " + jp.getSignature());
		log.info("[@AfterThrowing] logException : " + e);
	}
	
	/*
	 	6. After 어드바이스
	 	- 조인 포인트에서 처리가 완료된 후 실행된다.
	 */
	@After("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("[@After] endLog");
		log.info("[@After] endLog : " + jp.getSignature());
		log.info("[@After] endLog : " + Arrays.toString(jp.getArgs()));
	}
	
	
	
	/*
		7. Around 어드바이스
		- 조인 포인트 전 후에 실행된다.
		
		ProceedingJoinPoint : around 어드바이스에서 사용한다.
		
		스프링 프레임워크가 컨트롤 하고 있는 비즈니스로직 호출을 가로챈다.
		책임이 around 어드바이스로 전가되고 그래서 비즈니스 로직에 대한 정보를 around 어드바이스 매소드가 가지고 있어야하고,
		그정보를 스프링 컨테이너가 around 어드바이스 매소드로 넘겨주면 ProccedingJoinPoint 객체로 받아서 around 어드바이스가 컨트롤 시 활용함
	 */
	@Around("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		
		log.info("@Around : " + Arrays.toString(pjp.getArgs()));
		
		Object result = pjp.proceed();
		
		long endTime = System.currentTimeMillis();
		log.info("[@Around]pjpEnd : " + Arrays.toString(pjp.getArgs()));
		
		log.info("[@Around] : " + pjp.getSignature().getName() + "매소드 실행시간 : " + (endTime-startTime));
		
		return result;
	}
	
	/*
	 	8. 매소드 정보 획득
	 	- @Before 어노테이션 붙은 매소드는 joinpoint라는 매개변수를 통해 실행 중인 매서드의 정보를 구할수 있다.
	 	> @Before 어드바이스에서 테스트 진행
	 */
	
	
}









































