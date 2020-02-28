package com.trw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableSwagger2
@EnableAutoConfiguration
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class,args);
	}
}
