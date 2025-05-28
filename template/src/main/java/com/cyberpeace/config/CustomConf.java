/**
 * 这段代码是一个Spring配置类，用于加载自定义配置项。以下是代码的简要分析： 
 * 1. **类注解**： 
 * - `@Configuration`：表明这是一个Spring配置类，允许使用`@Bean`注解来定义Spring容器中的bean。 
 * 2. **方法`getSpringContextUtil`**： 
 * - 使用`@Bean`注解，将`SpringContextUtil`实例注册为Spring容器中的一个单例bean。 
 * - 返回一个新的`SpringContextUtil`对象。 
 * 3. **方法`getConfigUtil`**： 
 * - 使用`@Bean`注解，将`PropertyPlaceholderConfigurer`实例注册为Spring容器中的一个单例bean。 
 * - 创建了一个`ConfigUtil`实例（注意这里可能是一个命名错误，应该是`PropertyPlaceholderConfigurer`而非`ConfigUtil`）。 
 * - 定义了一系列资源文件的位置，这些文件包含了应用程序的配置属性。 
 * 4. **潜在问题**： 
 * - `PropertyPlaceholderConfigurer configurer = new ConfigUtil();` 这一行代码可能存在错误，应该是`PropertyPlaceholderConfigurer`而不是`ConfigUtil`。 
 * - 资源文件路径的加载逻辑需要确保文件在类路径下存在，否则可能导致运行时异常。 
 * 总结：该类主要用于通过Spring框架加载多个属性文件，并提供对Spring上下文和配置工具的访问。 
**/
package com.cyberpeace.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.SpringContextUtil;

/**
 * 自定义配置项
 *
 * @author yinqingrong
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Configuration
public class CustomConf {

	/**
	 * 获取春天上下文util
	 *
	 * @return Spring上下文实用程序
	 * @throws
	 * @see SpringContextUtil
	 */
	@Bean
	public static SpringContextUtil getSpringContextUtil() {
		return new SpringContextUtil();
	}

	/**
	 * 获取配置util
	 *
	 * @return 配置占 位 符配置器
	 * @throws
	 * @see PropertyPlaceholderConfigurer
	 */
	@Bean
	public static PropertyPlaceholderConfigurer getConfigUtil() {
		PropertyPlaceholderConfigurer configurer = new ConfigUtil();

		Resource[] resources = new ClassPathResource[] {
				new ClassPathResource("modules/integration.properties"),
				new ClassPathResource("modules/oj.properties"),
				new ClassPathResource("modules/cr.properties"),
				new ClassPathResource("modules/rtks.properties"),
				new ClassPathResource("modules/common.properties"),
				new ClassPathResource("modules/evaluate.properties"),
				new ClassPathResource("application.properties"),
				new ClassPathResource("modules/ojj.properties"),
				new ClassPathResource("modules/errorMessageZH.properties"),
				new ClassPathResource("modules/errorMessageEN.properties") };
		configurer.setLocations(resources);

		return configurer;
	}

}
