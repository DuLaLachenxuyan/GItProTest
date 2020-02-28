package com.trw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName: SwaggerConfig
 * @Description: 土融网测试使用Swagger2构建 API
 * @author luojing
 * @date 2018年6月29日 下午5:12:02
 *
 */
@Configuration
public class Swagger2Config {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
	}

	// 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 页面标题
				.title("土融网测试使用构建  API")
				// 创建人
				.contact(new Contact("LJ", "", ""))
				// 版本号
				.version("1.0")
				// 描述
				.description("API 描述").build();
	}

}