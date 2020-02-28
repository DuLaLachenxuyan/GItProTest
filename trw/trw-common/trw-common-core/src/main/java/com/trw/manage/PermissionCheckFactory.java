package com.trw.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trw.model.TrwTAgent;
import com.trw.util.CollectionKit;
import com.trw.util.HttpKit;
import com.trw.util.SpringContextHolder;

/**
 * 权限自定义检查
 */
@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class PermissionCheckFactory implements ICheck {

    public static ICheck me() {
        return SpringContextHolder.getBean(ICheck.class);
    }

    @Override
    public boolean check(Object[] permissions) {
    	TrwTAgent user = ShiroKit.getUser();
        if (null == user) {
            return false;
        }
        String join = CollectionKit.join(permissions, ",");
        if (ShiroKit.hasAnyRoles(join)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAll() {
        HttpServletRequest request = HttpKit.getRequest();
        TrwTAgent user = ShiroKit.getUser();
        if (null == user) {
            return false;
        }
        String requestURI = request.getRequestURI();
        String[] str = requestURI.split("/");
        if (str.length > 3) {
            requestURI = "/" + str[1] + "/" + str[2];
        }
        if (ShiroKit.hasPermission(requestURI)) {
            return true;
        }
        return false;
    }

}
