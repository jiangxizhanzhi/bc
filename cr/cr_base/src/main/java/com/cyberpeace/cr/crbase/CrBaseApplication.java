package com.cyberpeace.cr.crbase;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 该代码是一个Spring Boot应用程序的入口类，主要功能如下： 
 * 1. **注解说明**： 
 * - `@ServletComponentScan`：启用对自定义Servlet、Filter、Listener等组件的扫描。 
 * - `@SpringBootApplication`：标注为Spring Boot应用主类，包含`@Configuration`、`@EnableAutoConfiguration`和`@ComponentScan`。这里还通过`exclude`排除了`DataSourceAutoConfiguration`自动配置。 
 * - `scanBasePackages`：指定扫描的基础包路径为`com.cyberpeace`及其子包。 
 * 2. **功能**： 
 * - 排除了数据库相关的自动配置（`DataSourceAutoConfiguration`），可能因为该项目不需要数据库支持。 
 * - 扫描`com.cyberpeace`包及其子包下的所有Spring组件。 
 * 3. **运行逻辑**： 
 * - `main`方法通过`SpringApplication.run`启动Spring Boot应用。 
 * 总结：这是一个无数据库依赖的Spring Boot应用启动类，用于加载并运行基于`com.cyberpeace`包结构的Spring组件。 
**/
@ServletComponentScan
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
        scanBasePackages = { "com.cyberpeace" })
public class CrBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrBaseApplication.class, args);
    }

}

