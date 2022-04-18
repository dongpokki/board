package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 회원전용 페이지(url)를 요청할 경우 그 전에 로그인이 되었는지 확인할 수 있도록 LoginCheckInterceptor 클래스가 정보를 낚아챈다.
// 회원전용 처리 이전에 선 수행되는 클래스 - HandlerInterceptorAdapter 상속받고, preHandle메서드 재정의해줘야 함.
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
		
		logger.info("<<LoginCheckInterceptor 진입 / 회원전용 페이지 진입 전 interceptor 선 수행>>");
		
		//로그인 여부 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("user_num") == null) {
			//로그인이 되지 않은 상태
			response.sendRedirect(request.getContextPath()+"/member/login.do");
			return false;
		}
		
		return true;
	}
	
	
}