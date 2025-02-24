package com.cyberpeace.oj.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class OjPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjPracticeApplication.class, args);
	}

}
