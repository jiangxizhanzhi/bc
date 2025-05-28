package com.cyberpeace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * ojapplication
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */// @EnableCaching
// @EnableScheduling
@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class OJApplication {

	/**
	 * 主要
	 *
	 * @param args 参数
	 * @throws
	 * @see String[]
	 */
	public static void main(String[] args) {
		SpringApplication.run(OJApplication.class, args);
	}

}
