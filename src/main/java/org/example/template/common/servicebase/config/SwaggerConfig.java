package org.example.template.common.servicebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration // 标注是配置类的注解
@EnableSwagger2 // swagger注解
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("acl模块")
                .apiInfo(apiInfo())
                .select()
                // 设置扫描的基础包
                .apis(RequestHandlerSelectors.basePackage("org.example.template.service.acl.controller"))
                // 设置基础包下要扫描的路径规则
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("web-service-template接口文档")
                .description("web-service-template项目的接口文档")
                .version("1.0")
                .build();
    }

}
