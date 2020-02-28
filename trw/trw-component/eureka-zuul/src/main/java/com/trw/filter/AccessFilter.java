package com.trw.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @ClassName: AccessFilter
 * @Description: Zuul过滤器
 * @author luojing
 * @date 2018年6月29日 下午6:48:51
 *
 */
@Component
public class AccessFilter extends ZuulFilter {

	private final static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
//		HttpServletResponse rep = ctx.getResponse();

		logger.info(String.format("%s AccessFilter request to %s", request.getMethod(),
				request.getRequestURL().toString()));

		if (request.getRequestURI().contains("favicon.ico")) {
			return null;
		}

		ctx.setSendZuulResponse(true);// 对该请求进行路由
		ctx.setResponseStatusCode(200);
		ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
		return null;

		/*
		 * if (null != username && username.equals("chhliu")) {//
		 * 如果请求的参数不为空，且值为chhliu时，则通过 ctx.setSendZuulResponse(true);// 对该请求进行路由
		 * ctx.setResponseStatusCode(200); ctx.set("isSuccess", true);//
		 * 设值，让下一个Filter看到上一个Filter的状态 return null; } else {
		 * ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
		 * ctx.setResponseStatusCode(401);// 返回错误码
		 * ctx.setResponseBody("{\"result\":\"username is not correct!\"}");// 返回错误内容
		 * ctx.set("isSuccess", false); return null; }
		 */
	}

	@Override
	public boolean shouldFilter() {
		return true;// 是否执行该过滤器，此处为true，说明需要过滤
	}

	@Override
	public int filterOrder() {
		return 0;// 优先级为0，数字越大，优先级越低
	}

	@Override
	public String filterType() {
		/*
		 * pre：可以在请求被路由之前调用 route：在路由请求时候被调用 post：在route和error过滤器之后被调用
		 * error：处理请求时发生错误时被调用
		 */
		return "pre";// 前置过滤器
	}
}
