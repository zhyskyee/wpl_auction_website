package utd.wpl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	
	//执行时机：controller方法没有被执行，ModelAndView没有返回
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//如果是访问主页或者是登录操作，或者注册操作，放行
//		String uri = request.getRequestURI();
//		if (uri.equals("/") || uri.contains("/login") || uri.contains("/register") || uri.contains("curitem")) {
//			return true;
//		}
//		// 判断session中是否有登录信息，如果没有则跳转到登录页面
//		if (request.getSession().getAttribute("user") != null) {
//			return true;
//		}
//		response.sendRedirect("/");
//		return false;
		return true;
	}
	
	//执行时机：controller方法已经执行，ModelAndView没有返回
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {}
	
	//执行时机：controller方法已经执行，ModelAndView已经返回
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {}

}
