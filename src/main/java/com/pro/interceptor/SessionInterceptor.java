package com.pro.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器(通过session的属性name判断是否登录
 * @author Administrator
 *
 */
@Component
public class SessionInterceptor implements HandlerInterceptor{

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	System.err.println("RequestURI-->"+request.getRequestURI());
    	
    	//首页路径以及登录放行
//        if ("/login".equals(request.getRequestURI())) {
//            return true;}
//    	
    	Object user = request.getSession().getAttribute("user");
        // 如果获取的request的session中的loginUser参数为空（未登录），就返回登录页，否则放行访问
        if (user == null) {
            // 未登录，给出错误信息，
            request.setAttribute("msg","无权限请先登录");
            // 获取request返回页面到登录页
            response.sendRedirect("/login");
            return false;
        } else {
            // 已登录，放行
            return true;
        }
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
	    System.err.println("postHandle");
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
	    System.err.println("afterCompletion");
	}
}
