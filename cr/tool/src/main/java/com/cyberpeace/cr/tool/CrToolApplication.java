package com.cyberpeace.cr.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 这段代码是一个Spring Boot应用的主类。以下是简要分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.cr.tool;`：声明了这个类所在的包名。 
 * 2. **导入语句**： 
 * - 导入了Spring Boot相关的注解和类。 
 * 3. **注解**： 
 * - `@ServletComponentScan`：用于扫描Servlet组件（如Servlet、Filter、Listener）。 
 * - `@SpringBootApplication`：这是一个组合注解，包含了`@Configuration`、`@EnableAutoConfiguration` 和 `@ComponentScan` 注解。 
 * - `exclude = { DataSourceAutoConfiguration.class }`：排除数据源自动配置。 
 * - `scanBasePackages = { "com.cyberpeace" }`：指定扫描的基础包路径。 
 * 4. **主方法**： 
 * - `public static void main(String[] args)`：定义了应用的入口点。 
 * - `SpringApplication.run(CrToolApplication.class, args);`：启动Spring Boot应用。 
 * 总结：这是一个配置好并可运行的Spring Boot应用，主要用于启动应用并扫描指定的包路径下的Servlet组件。 
**/
@ServletComponentScan
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class CrToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrToolApplication.class, args);
	}

}
