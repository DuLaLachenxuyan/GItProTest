package com.trw.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ServletContext监听器
 *
 */
@WebListener
public class ConfigListener implements ServletContextListener {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
    private static Map<String, String> conf = new HashMap<>();

    public static Map<String, String> getConf() {
        return conf;
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        conf.clear();
    }

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        ServletContext sc = evt.getServletContext();
        //项目发布,当前运行环境的绝对路径
//        conf.put("realPath", sc.getRealPath("/").replaceFirst("/", ""));
        conf.put("realPath", sc.getRealPath("/"));

        //servletContextPath,默认""
        conf.put("contextPath", sc.getContextPath());
    }

}
