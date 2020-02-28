package com.trw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringCloudApplication
@EnableEurekaClient
@EnableSwagger2
@EnableAutoConfiguration
public class TaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class,args);
	}
}
