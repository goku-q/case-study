package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {

	@Override
	/**
	 * 在请求进入handler之前处理，一般用于权限，登录等验证
	 * 返回true才继续执行
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("模拟拦截跳转");
		//转发
		request.getRequestDispatcher("/bye").forward(request, response);
		//重定向
		//response.sendRedirect("/bye");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	//在请求进入handler后，返回modelandview之前执行
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	/**
	 * 在请求handler方法执行完成后执行，一般应用于日志和异常
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
