package com.cyberpeace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

// @EnableCaching
// @EnableScheduling
@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class OJApplication {

	public static void main(String[] args) {
		SpringApplication.run(OJApplication.class, args);
	}

}
