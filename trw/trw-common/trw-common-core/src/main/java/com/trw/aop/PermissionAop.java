package com.trw.aop;

import java.lang.reflect.Method;

import javax.naming.NoPermissionException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.trw.annotion.Permission;
import com.trw.manage.PermissionCheckManager;

@Component
@Aspect
public class PermissionAop {
	/** 切面点 */
	private final String POINT_CUT = "@annotation(com.trw.annotion.Permission)";

	@Pointcut(POINT_CUT)
	private void pointcut() {
	}

	/**
	 * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
	 * 
	 * @param joinPoint
	 */
	@Around(value = POINT_CUT)
	public Object doPermission(ProceedingJoinPoint point) throws Throwable {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		Permission permission = method.getAnnotation(Permission.class);
		
		Object[] permissions = permission.value();
        if (permissions == null || permissions.length == 0) {
            //检查全体角色
            boolean result = PermissionCheckManager.checkAll();
            if (result) {
                return point.proceed();
            } else {
                throw new NoPermissionException();
            }
        } else {
            //检查指定角色
            boolean result = PermissionCheckManager.check(permissions);
            if (result) {
                return point.proceed();
            } else {
                throw new NoPermissionException();
            }
        }
		 
	}
}
