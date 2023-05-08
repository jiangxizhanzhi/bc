package com.cyberpeace.config.swagger;

import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cyberpeace.common.util.ConfigUtil;

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

@Configuration
@EnableOpenApi
public class SwaggerConfig {

//	@Bean
//	public Docket apiTemplate() {
//		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.demo"))
//				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
//				.groupName("demo");
//	}

	@Bean
	public Docket apiCs() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.cs"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("cs");
	}

	@Bean
	public Docket apiSituation() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.situation"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("situation");
	}

	@Bean
	public Docket apiGateway() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.gateway"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("outer");
	}

//	@Bean
//	public Docket apiBehavioralSimulation() {
//		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.bs"))
//				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
//				.directModelSubstitute(LocalTime.class, String.class).groupName("bs");
//	}

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

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("网络靶场项目文档").description("有问题请联系朱天尧").version("v1.0")
				.build();
	}
}