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
 * Swagger 配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

	/**
	 * Api模板
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiTemplate() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.demo"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("demo");
	}

	/**
	 * Api靶场
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiCr() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.cr"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("cr");
	}

	/**
	 * ApiCS
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiCs() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.cs"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("cs");
	}

	/**
	 * ApiOJ
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiOj() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.oj"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("oj");
	}

	/**
	 * Api空间
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiSpace() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.space"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("space");
	}

	/**
	 * Api地震魔
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiEs() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.evaluate"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("es");
	}

	/**
	 * Api网关
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiGateway() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.gateway"))
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
				.groupName("outer");
	}

	/**
	 * Api框架
	 *
	 * @return 案卷
	 * @throws
	 * @see Docket
	 */
	@Bean
	public Docket apiFrame() {
		return getDocket().apis(RequestHandlerSelectors.basePackage("com.cyberpeace.frame"))
						  .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
						  .groupName("frame");
	}

	/**
	 * 获取案卷
	 *
	 * @return Api选择器建筑工人
	 * @throws
	 * @see ApiSelectorBuilder
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
	 * Api信息
	 *
	 * @return Api信息
	 * @throws
	 * @see ApiInfo
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("网络靶场项目文档").description("有问题请联系朱天尧").version("v1.0")
				.build();
	}
}