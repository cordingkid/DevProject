package kr.or.ddit.controller.intercept;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessLoggingInterceptor extends HandlerInterceptorAdapter{

	PrintWriter writer;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("프리핸들 진행...!");
		File file = new File("c:/logs/ddit_logging.log");
		writer = new PrintWriter(new FileWriter(file,true),true);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String requestURI = request.getRequestURI();
		log.info("requestURI : " + requestURI);
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		
		Class clazz = method.getDeclaringClass();
		
		String className = clazz.getName();				// kr.or.ddit.controller.BoardController와 같은 녀석
		String classSimpleName = clazz.getSimpleName();	// BoardController 같은녀석
		String methodName = method.getName();			// boardList와 같은 매서드
		
		writer.printf("현재 일시 : %s %n", getCurrentTime());
		writer.printf("Access Controller : %s %n", className + "." + methodName);
//		writer.flush();
	}

	private Object getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(cal.getTime());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	
}
