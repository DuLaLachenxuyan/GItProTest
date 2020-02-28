package com.trw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringCloudApplication
@EnableEurekaClient
@EnableSwagger2
@MapperScan("com.trw.mapper")
@EnableAutoConfiguration
@EnableFeignClients(basePackages= {"com.trw.feign"})
public class NewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class,args);
	}
}
