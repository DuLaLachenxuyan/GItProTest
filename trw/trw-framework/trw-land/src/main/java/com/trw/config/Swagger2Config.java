package com.trw.config;
//swagger2的配置文件，在项目的启动类的同级文件建立
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
* @ClassName: SwaggerConfig 
* @Description: 土融网测试使用构建  API
* @author luojing
* @date 2018年6月29日 下午5:12:02 
*
 */
@Configuration
public class Swagger2Config {
//swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
    	List<Parameter> pars = new ArrayList<Parameter>();  
    	ParameterBuilder token = new ParameterBuilder();
        token.name("token").description("用户Token").modelRef(new ModelRef("string"))
    	.parameterType("header").required(false).build(); //header中的ticket参数非必填，传空也可以
        ParameterBuilder severtoken = new ParameterBuilder();
        severtoken.name("severtoken").description("服务商Token").modelRef(new ModelRef("string"))
    	.parameterType("header").required(false).build(); //header中的ticket参数非必填，传空也可以
    	pars.add(token.build());    //根据每个方法名也知道当前方法在设置什么参数
    	pars.add(severtoken.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.trw.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("土地中心 API")
                //创建人
                .contact(new Contact("LJ", "", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
 
 
}