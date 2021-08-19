package com.cyberpeace.oj.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(SecurityContext.builder()
                        .securityReferences(Arrays.asList(SecurityReference.builder()
                                .reference("Authorization")
                                .scopes(new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})
                                .build()))
                        .build()))
                .securitySchemes(Arrays.asList(new ApiKey("Authorization", "Authorization", "header")))
                .select().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.oj"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RTKS项目文档")
                .description("有问题请联系朱天尧")
                .version("v1.0")
                .build();
    }
}