package com.cyberpeace.oj.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 这段代码定义了一个Spring Boot应用程序，名为`OjCourseApplication`。以下是详细分析： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.oj.course; 
 * ``` 
 * 该类位于`com.cyberpeace.oj.course`包中。 
 * 2. **导入必要的类**： 
 * ```java 
 * import org.springframework.boot.SpringApplication; 
 * import org.springframework.boot.autoconfigure.SpringBootApplication; 
 * import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration; 
 * import org.springframework.boot.web.servlet.ServletComponentScan; 
 * ``` 
 * 这些导入语句引入了Spring Boot框架中的必要类和注解。 
 * 3. **注解**： 
 * - `@ServletComponentScan`：启用对Servlet组件（如过滤器、监听器等）的自动扫描。 
 * - `@SpringBootApplication`：这是一个组合注解，包含了`@Configuration`、`@EnableAutoConfiguration`和`@ComponentScan`注解。它还排除了`DataSourceAutoConfiguration`类，并设置了基础扫描包为`com.cyberpeace`。 
 * ```java 
 * @SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }, 
 * scanBasePackages = { "com.cyberpeace" }) 
 * ``` 
 * 4. **主方法**： 
 * ```java 
 * public static void main(String[] args) { 
 * SpringApplication.run(OjCourseApplication.class, args); 
 * } 
 * ``` 
 * `main`方法是应用程序的入口点，它使用`SpringApplication.run()`方法启动Spring Boot应用。 
 * 总结：这段代码是一个配置好的Spring Boot应用，用于扫描并运行位于`com.cyberpeace`包及其子包下的组件，并且不使用默认的数据库配置。 
**/
@ServletComponentScan
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
        scanBasePackages = { "com.cyberpeace" })
public class OjCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjCourseApplication.class, args);
    }

}
