package com.cyberpeace.cr.crbase;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
        scanBasePackages = { "com.cyberpeace" })
public class CrBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrBaseApplication.class, args);
    }

}
