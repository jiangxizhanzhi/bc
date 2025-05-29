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
/**
 * 该代码是一个用于配置Swagger的Spring Boot应用配置类。以下是代码的主要功能和结构分析： 
 * 1. **配置类与注解**： 
 * - 类 `SwaggerConfig` 使用了 `@Configuration` 注解，表明这是一个Spring配置类。 
 * - 使用了 `@EnableOpenApi` 注解，启用Swagger OpenAPI支持。 
 * 2. **Docket Bean定义**： 
 * - 定义了多个 `@Bean` 方法（如 `apiTemplate`, `apiCr`, `apiCs` 等），每个方法返回一个 `Docket` 对象，用于配置不同的API分组。 
 * - 每个 `Docket` 对象通过 `apis()` 和 `paths()` 方法指定扫描的包路径和路径规则，并设置分组名称（如 `"demo"`, `"cr"` 等）。 
 * 3. **通用方法 `getDocket()`**： 
 * - 提供了一个私有方法 `getDocket()`，用于复用Docket的配置逻辑。 
 * - 从配置文件中读取 `ojj.apiDoc` 属性，决定是否启用Swagger文档。 
 * - 配置了安全上下文 (`securityContexts`) 和安全方案 (`securitySchemes`)，支持基于 `Authorization` 头部的认证。 
 * 4. **ApiInfo 配置**： 
 * - `apiInfo()` 方法定义了Swagger文档的基本信息，包括标题、描述、版本等。 
 * 5. **模块化设计**： 
 * - 每个模块的API配置独立且可复用，便于扩展和维护。 
 * 总结：该代码通过多模块配置实现了Spring Boot项目的Swagger文档生成，支持不同的API分组，并集成了安全认证机制。 
**/
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
