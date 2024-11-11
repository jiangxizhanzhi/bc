package com.cyberpeace.config.swagger;

import com.cyberpeace.common.util.ConfigUtil;
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
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
/**
 * Swagger配置类，用于全局启用OpenAPI并定义多个API分组
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /**
     * 创建Docket实例，配置rtks模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiRtks() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.rtks"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("rtks");
    }

    /**
     * 创建Docket实例，配置demo模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiTemplate() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.demo"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("demo");
    }

    /**
     * 创建Docket实例，配置cr模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiCr() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.cr"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("cr");
    }

    /**
     * 创建Docket实例，配置cs模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiCs() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.cs"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("cs");
    }

    /**
     * 创建Docket实例，配置oj模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiOj() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.oj"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("oj");
    }

    /**
     * 创建Docket实例，配置evaluate模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiEs() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.evaluate"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("es");
    }

    /**
     * 创建Docket实例，配置situation模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiSituation() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.situation"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("situation");
    }

    /**
     * 创建Docket实例，配置gateway模块的API文档
     * @return Docket实例，用于配置和构建API文档
     */
    @Bean
    public Docket apiGateway() {
        return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.gateway"))
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .groupName("outer");
    }

    /**
     * 获取Docket实例的私有方法，用于配置通用的API文档信息
     * @return ApiSelectorBuilder实例，用于进一步配置API选择器
     */
    private ApiSelectorBuilder getDocket() {
        String apiDoc = ConfigUtil.getContextProperty("ojj.apiDoc");

        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                .enable(Boolean.parseBoolean(apiDoc))
                .securityContexts(Arrays.asList(SecurityContext.builder()
                        .securityReferences(Arrays.asList(SecurityReference.builder()
                                .reference("Authorization")
                                .scopes(new AuthorizationScope[] {
                                        new AuthorizationScope("global", "accessEverything") })
                                .build()))
                        .build()))
                .securitySchemes(
                        Arrays.asList(new ApiKey("Authorization", "Authorization", "header")))
                .select();
    }

    /**
     * 构建API文档的通用信息，如标题、描述和版本
     * @return ApiInfo实例，包含API文档的通用信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("网络靶场项目文档").description("有问题请联系朱天尧").version("v1.0")
                .build();
    }
}
