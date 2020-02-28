package com.trw.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trw.enums.BizExceptionEnum;
import com.trw.enums.ErrorTip;
import com.trw.util.RenderUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ShiroRoleAuthorizationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
		if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isDebugEnabled()) {
                    log.debug("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Login page view.");
                }
                return true;
            }
        } else {
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse resp = (HttpServletResponse) response;
            if(req.getMethod().equals(RequestMethod.OPTIONS.name())) {
                resp.setStatus(HttpStatus.OK.value());
                return true;
            }
            RenderUtil.renderJson(resp, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return false;
        }
		 
	}
 
}