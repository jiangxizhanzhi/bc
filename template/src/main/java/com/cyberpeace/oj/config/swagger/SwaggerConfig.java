package com.cyberpeace.oj.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cyberpeace.oj.common.constant.WebConstant;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
		parameterBuilder.name(WebConstant.AUTHORIZATION_HEADER).description("请求头")
				.in(ParameterType.HEADER).required(false).build();

		List<RequestParameter> parameters = new ArrayList<>();
		parameters.add(parameterBuilder.build());

		return new Docket(DocumentationType.OAS_30).select()
				// apis： 添加swagger接口提取范围
				.apis(RequestHandlerSelectors.basePackage("com.cyberpeace.oj"))
				.paths(PathSelectors.any()).build().globalRequestParameters(parameters);
	}
}