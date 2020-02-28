package com.trw.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
/**
* @ClassName: Swagger2DocConfig 
* @Description: Swagger2资源文档配置   通过遍历路由方式自动添加所有微服务 API 文档
* @author luojing
* @date 2018年6月30日 下午1:18:49 
*
 */
@Component
@Primary
public class Swagger2DocConfig implements SwaggerResourcesProvider{
	
	private final static Logger logger = LoggerFactory.getLogger(Swagger2DocConfig.class);
	
	private final RouteLocator routeLocator;

    public Swagger2DocConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        logger.info(Arrays.toString(routes.toArray()));
        routes.forEach(route -> {
        	if(route.getId().indexOf("trw") == -1) {
        		resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"),"2.0"));
        	}
        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    
	/*
	 * 手动添加
	 * 
	 * @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
        resources.add(swaggerResource("用户中心", "/user/v2/api-docs", "2.0"));
        resources.add(swaggerResource("订单中心", "/order/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }*/

}
