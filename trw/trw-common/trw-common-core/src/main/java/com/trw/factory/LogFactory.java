package com.trw.factory;

import java.util.Date;

import com.trw.enums.LogSucceed;
import com.trw.enums.LogType;
import com.trw.model.LoginLog;
import com.trw.model.OperationLog;
import com.trw.util.DateTimeKit;
import com.trw.util.ToolUtil;
 

/**
 * 日志对象创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogFactory {

    /**
     * 创建操作日志
     */
    public static OperationLog createOperationLog(LogType logType, String userId, String bussinessName, String clazzName, String methodName, String msg, LogSucceed succeed) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogtype(logType.getMessage());
        operationLog.setLogname(bussinessName);
        operationLog.setUserid(userId);
        operationLog.setClassname(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setCreatetime(new Date());
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMsg(msg);
        return operationLog;
    }

    /**
     * 创建登录日志
     */
    public static LoginLog createLoginLog(LogType logType, String userId, String msg, String ip) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLogname(logType.getMessage());
        loginLog.setUserid(userId);
        loginLog.setCreatetime(DateTimeKit.date());
        loginLog.setSucceed(LogSucceed.SUCCESS.getMessage());
        loginLog.setIp(ip);
        loginLog.setMsg(msg);
        loginLog.setId(ToolUtil.generateId("LOG"));
        return loginLog;
    }
}
