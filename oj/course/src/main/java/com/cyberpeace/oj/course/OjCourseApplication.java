package com.cyberpeace.oj.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
        scanBasePackages = { "com.cyberpeace" })
public class OjCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjCourseApplication.class, args);
    }

}