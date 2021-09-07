package com.cyberpeace;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cyberpeace.common.util.CustomExceptionHandlerResolver;

@EnableScheduling
@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class OJApplication implements WebMvcConfigurer {

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		// 劫持spring的异常处理
		resolvers.clear();
		resolvers.add(new CustomExceptionHandlerResolver());
	}

	public static void main(String[] args) {
		SpringApplication.run(OJApplication.class, args);
	}

}
