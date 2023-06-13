package kr.or.ddit.controller.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final String USER_INFO = "userInfo";
	
	
	// 지정된 컨트롤러의 동작 이전에 가로채는 역할을 함
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandler...!");
		
		String requestURL = request.getRequestURL().toString();	// http://localhost/login1
		String requestURI = request.getRequestURI();			// /login1
		
		log.info("requestURL :" + requestURL);
		log.info("requestURI :" + requestURI);
		
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		
		log.info("Bean : " + method.getBean()); // kr.or.ddit.controller.login.LoginInterceptor
		log.info("method : " + methodObj); //public java.lang.String kr.or.ddit.controller.login.loginController.loginForm()
		
		HttpSession session = request.getSession();
		if (session.getAttribute(USER_INFO) != null) {
			session.removeAttribute(USER_INFO);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("포수트핸들...!");
		
		
		String requestURL = request.getRequestURL().toString();	// http://localhost/login1
		String requestURI = request.getRequestURI();			// /login1
		
		log.info("requestURL :" + requestURL);
		log.info("requestURI :" + requestURI);
		
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		
		log.info("Bean : " + method.getBean()); // kr.or.ddit.controller.login.LoginInterceptor
		log.info("method : " + methodObj); 
		
		HttpSession session = request.getSession();
		
		
		// 컨트롤러의 매소드를 거쳣다ㅏ posthandel로 넘어오면서 전달된 user라는 거에value로 member담긴값이
		// Model에 담겨져 있다. 그중에user로 넘긴 값이 로그인 후 인증된 회원 1명의 정보가 담긴 MemberVO 자바빈즈 객체가 되고
		// 객체가 널이 아닌경우 메인화면으로 리다이랙트 한다.
		ModelMap modelMap = modelAndView.getModelMap();// 데이터 전달자
		Object member = modelMap.get("user");
		
		
		if (member != null) {
			log.info("member : " + member);
			log.info("member 널 아님");
			session.setAttribute(USER_INFO, member);
			response.sendRedirect("/");
		}
	}

	// DispatcherServlet의 화면 처리가 완료된 상태에서 처리한다.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("애프터컴프리션...!");

		String requestURL = request.getRequestURL().toString(); // http://localhost/login1
		String requestURI = request.getRequestURI(); // /login1

		log.info("requestURL :" + requestURL);
		log.info("requestURI :" + requestURI);

		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
	}

}








































