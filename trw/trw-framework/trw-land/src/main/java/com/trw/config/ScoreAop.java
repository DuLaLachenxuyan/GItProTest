package com.trw.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.trw.annotion.CountScore;
import com.trw.feign.MsgFeignApi;
import com.trw.vo.ScoreMsg;

@Component
@Aspect
public class ScoreAop {
	/** 切面点 */
	private final String POINT_CUT = "@annotation(com.trw.annotion.CountScore)";

	@Autowired
	private MsgFeignApi msgFeignApi;

	@Pointcut(POINT_CUT)
	private void pointcut() {
	}

	/**
	 * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
	 * 
	 * @param joinPoint
	 */
	@After(value = POINT_CUT)
	public void doAfterAdvice(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();// 获取request
		String faciid = (String) request.getAttribute("faciid");
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		CountScore countScore = method.getAnnotation(CountScore.class);
//		int score = countScore.score(); // 分数
//		String type = countScore.type(); // 类型
		String ruleId = countScore.rule(); //规则

//		ScoreMsg msg = new ScoreMsg(faciid,ruleId,type,score);
		ScoreMsg msg = new ScoreMsg(faciid,ruleId);
		msgFeignApi.scoreMsg(msg);
	}
}
