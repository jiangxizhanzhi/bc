/**
 * 这段代码是一个Spring Boot应用程序的入口类。以下是对代码的简要分析：
 * 1. **包声明**：`package com.cyberpeace.cr.evaluation;`
 * 表示该类所在的包名为`com.cyberpeace.cr.evaluation`。
 * 2. **导入的类**：
 * - `CrBaseApplication`：可能是某个基础应用类。
 * - `SpringApplication` 和 `SpringBootApplication`：Spring Boot的核心类和注解，用于启动Spring Boot应用。
 * - `DataSourceAutoConfiguration`：Spring Boot的自动配置类，用于数据源配置。这里通过`exclude`排除了它。
 * - `ServletComponentScan`：用于扫描Servlet组件（如过滤器、监听器等）。
 * 3. **注解说明**：
 * - `@ServletComponentScan`：启用对Servlet组件的扫描。
 * - `@SpringBootApplication`：这是一个组合注解，包含`@Configuration`、`@EnableAutoConfiguration`和`@ComponentScan`。
 * - `exclude = { DataSourceAutoConfiguration.class }`：排除数据源自动配置。
 * - `scanBasePackages = { "com.cyberpeace" }`：指定Spring扫描的基础包为`com.cyberpeace`及其子包。
 * - `main`方法：标准的Spring Boot主方法，用于启动应用。
 * 4. **功能总结**：
 * 这是一个Spring Boot应用的启动类，通过排除`DataSourceAutoConfiguration`来避免默认的数据源配置，并且扫描`com.cyberpeace`包下的所有组件。
**/
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
