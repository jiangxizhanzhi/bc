/**
 * 该代码定义了一个名为 `ConfigReader` 的工具类，用于从配置中读取字符串和整数类型的值，并在配置缺失或格式不正确时抛出异常。以下是代码的主要分析和总结： 
 * ### 功能分析： 
 * 1. **功能概述**： 
 * - 提供静态方法用于读取配置。 
 * - 支持读取字符串类型和整数类型的配置值。 
 * - 如果配置不存在或格式错误，则记录日志并抛出自定义异常。 
 * 2. **核心方法**： 
 * - `read(String name)`：根据名称读取配置值。如果值为空，记录错误日志并抛出 `BusinessException` 异常。 
 * - `readInt(String name)`：调用 `read(String name)` 获取字符串值，并尝试将其转换为整数。如果转换失败，记录错误日志并抛出异常。 
 * 3. **依赖项**： 
 * - 使用了 `ConfigUtil.getContextProperty(name)` 方法从配置中获取值。 
 * - 使用了自定义异常 `BusinessException` 和错误码 `ErrorCode`。 
 * 4. **日志记录**： 
 * - 使用了 `log.error` 记录错误信息，便于问题排查。 
 * 5. **异常处理**： 
 * - 对于空值和非法值，通过 `BusinessException` 抛出异常，便于上层调用方处理。 
 * ### 总结： 
 * - **优点**： 
 * - 代码结构清晰，职责单一。 
 * - 使用了异常机制和日志记录，增强了程序的健壮性。 
 * - 提供了统一的配置读取方式，避免重复代码。 
 * - **潜在改进点**： 
 * - 可以增加对其他数据类型（如布尔值、浮点数等）的支持。 
 * - 考虑对 `ConfigUtil.getContextProperty(name)` 的空值返回进行更详细的日志记录，便于定位问题来源。 
 * 总体而言，这段代码是一个简洁且实用的配置读取工具类，适合在项目中作为基础组件使用。 
**/
package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 配置读取器
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
class ConfigReader {

	/**
	 * 读
	 *
	 * @param name 名称
	 * @return {@code String }
	 */
	public static String read(String name) {
		String config = ConfigUtil.getContextProperty(name);
		if (config == null) {
			log.error("[{}] not configured!", name);
			throw new BusinessException(ErrorCode.NOT_CONFIGURED, name);
		}
		return config;
	}

	/**
	 * 读取 int
	 *
	 * @param name 名称
	 * @return {@code Integer }
	 */
	public static Integer readInt(String name) {
		String config = read(name);
		try {
			return new Integer(config);
		} catch (Exception e) {
			log.error("Invalid configuration for [{}]: [{}]!", name, config);
			throw new BusinessException(ErrorCode.INVALID_CONFIGURATION, name, config);
		}
	}
}
