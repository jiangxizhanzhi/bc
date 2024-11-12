package com.cyberpeace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ojapplication
 *
 * @author admin
 * @date 2024/11/12
 */
@EnableCaching
@EnableScheduling
@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class OJApplication {

	/**
	 * 主方法
	 *
	 * @param args args
	 */
	public static void main(String[] args) {
		SpringApplication.run(OJApplication.class, args);
	}

}
