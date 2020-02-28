package com.trw.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trw.constant.Constant;
import com.trw.enums.BizExceptionEnum;
import com.trw.enums.ErrorTip;
import com.trw.service.impl.RedisService;
import com.trw.util.RenderUtil;
import com.trw.util.StrKit;
import com.trw.vo.TokenData;



/***************
 * token验证拦截
 *
 */
@Component
public class TokenAuthorFilter implements Filter {

//	private static Logger logger = LoggerFactory.getLogger(TokenAuthorFilter.class);
	@Autowired
	private RedisService redisService;
    @Override
    public void destroy() {
    
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        String userId="";
        String token = req.getHeader("token");//header方式req.getParameter("token");
        if(token!=null) { //用户登录
        	  userId =redisService.getString(Constant.SESSIONPIX+token);
              redisService.set(Constant.SESSIONPIX + token, userId, 1800L,TimeUnit.SECONDS);
        }
        
        String method = ((HttpServletRequest) request).getMethod();
        if (method.equals("OPTIONS")) {
            rep.setStatus(HttpServletResponse.SC_OK);
        }else{
            if(req.getRequestURI().matches(".*/guest/.*")){
            	if(token!=null) {
            		if(!StrKit.isBlank(userId)){
            			TokenData tokenData =new TokenData(token,userId);
                        request.setAttribute("tokenData", tokenData);
                        chain.doFilter(request, response);
            		} else {
            			RenderUtil.renderJson(rep, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
            		}
            		return;
            	}
            	 RenderUtil.renderJson(rep, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            }else {
            	  chain.doFilter(request, response);
            }
        }
    }
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    
}
