package com.trw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import com.github.tobato.fastdfs.FdfsClientConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringCloudApplication
@EnableEurekaClient
@EnableSwagger2
@MapperScan("com.trw.mapper")
@EnableAutoConfiguration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@Import(FdfsClientConfig.class)
public class FileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class,args);
	}
}
