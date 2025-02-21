package com.cyberpeace.cr.evaluation;


import com.cyberpeace.cr.crbase.CrBaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
        scanBasePackages = { "com.cyberpeace" })
public class CrEvaluationApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrEvaluationApplication.class, args);
    }


}
