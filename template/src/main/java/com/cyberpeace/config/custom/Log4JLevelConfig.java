/**
 * 这段代码定义了一个名为 `Log4JLevelConfig` 的 Spring Bean，用于动态设置日志级别。以下是代码的主要功能和结构分析： 
 * ### 功能分析： 
 * 1. **日志级别配置**： 
 * - 通过 `SystemPropUtil.getProp("LOG_LEVEL")` 获取系统属性 `LOG_LEVEL` 的值，并将其存储在类变量 `LOG_LEVEL` 中。 
 * - 如果 `LOG_LEVEL` 的值为空，则不进行任何初始化操作。 
 * 2. **日志初始化**： 
 * - 使用 `@PostConstruct` 注解标记的 `init()` 方法会在 Spring 容器初始化时自动执行。 
 * - 根据获取到的日志级别 (`LOG_LEVEL`)，将日志记录器（Logger）的级别设置为对应的 `org.apache.log4j.Level` 值。 
 * - 设置了多个特定的日志记录器（如 `"mq"`, `"mp"`, `"resourceImport"`, `"eval"`, `"audit"`）的级别。 
 * 3. **日志输出**： 
 * - 在日志级别设置前后，使用 `log.info()` 输出日志信息，便于调试和监控。 
 * ### 技术点分析： 
 * - **Lombok 注解**： 
 * - 使用了 `@Slf4j` 注解，自动生成了 `log` 对象，简化了日志记录的代码书写。 
 * - **Spring 生命周期管理**： 
 * - 使用了 `@PostConstruct` 注解，确保在依赖注入完成后立即执行初始化逻辑。 
 * - **Apache Commons Lang**： 
 * - 使用了 `StringUtils.isBlank()` 方法来检查字符串是否为空或空白，增强代码健壮性。 
 * - **Log4j 集成**： 
 * - 通过 `Logger.getRootLogger().setLevel(level)` 和其他特定 Logger 的 `setLevel()` 方法，动态调整日志级别。 
 * ### 总结： 
 * 该类的核心作用是根据系统配置动态调整日志级别，适用于需要灵活控制日志输出场景的应用程序。代码结构清晰，结合了 Spring 框架和第三方库的功能，提高了开发效率和代码可维护性。 
**/
package com.cyberpeace.config.custom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * log4 jlevel 配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Slf4j
@Component
public class Log4JLevelConfig {

	/**
	 * 日志层级
	 *
	 * @see String
	 */
	String LOG_LEVEL = SystemPropUtil.getProp("LOG_LEVEL");

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	private void init() {
		if (StringUtils.isBlank(LOG_LEVEL)) {
			return;
		}

		log.info("Init log level! level=[{}].....", LOG_LEVEL);
		Level level = Level.toLevel(LOG_LEVEL);
		Logger.getRootLogger().setLevel(level);
		Logger.getLogger("mq").setLevel(level);
		Logger.getLogger("mp").setLevel(level);
		Logger.getLogger("resourceImport").setLevel(level);
		Logger.getLogger("eval").setLevel(level);
		Logger.getLogger("audit").setLevel(level);
		log.info("Init log level end!.....");
	}

}
