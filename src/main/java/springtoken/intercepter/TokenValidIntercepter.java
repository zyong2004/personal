package springtoken.intercepter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



/**
 * 验证token
 * @author zhangyong
 * 2015年9月28日
 */
public class TokenValidIntercepter extends HandlerInterceptorAdapter {

	private static Logger LOG = LoggerFactory.getLogger(TokenValidIntercepter.class);
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOG.info("===========================");
		
		if(!TokenHandler.validToken(request)){
			Map<String, Object> map = new HashMap<>();
			map.put("success", "false");
			map.put("errReason", "验证token失败");
			writeMessageUtf8(response, map);
			return false;
		}
		return true;
	}
	
	
	private void writeMessageUtf8(HttpServletResponse response, Map<String , Object> json) throws IOException  
	    {  
	        try  
	        {  
	            response.setCharacterEncoding("UTF-8");  
	            response.getWriter().print(json.toString());  
	        }  
	        finally  
	        {  
	            response.getWriter().close();  
	        }  
	    }  
	
}
