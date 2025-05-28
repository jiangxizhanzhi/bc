/**
 * 这段代码定义了一个工具类 `SystemPropUtil`，用于从 JVM 系统属性或操作系统环境变量中读取指定键对应的值。以下是代码的简要分析： 
 * 1. **类设计**： 
 * - 类被声明为 `private` 构造方法，确保无法实例化该类（工具类常见设计）。 
 * 2. **核心功能**： 
 * - 提供静态方法 `getProp(String key)`，用于获取指定键的值。 
 * - 方法首先尝试从 JVM 的系统属性 (`System.getProperty(key)`) 中获取值。 
 * - 如果获取不到，则从操作系统的环境变量 (`System.getenv(key)`) 中查找。 
 * 3. **依赖工具**： 
 * - 使用了 `org.apache.commons.lang3.StringUtils` 来判断字符串是否为空或空白。 
 * 4. **优点**： 
 * - 层次清晰：优先从 JVM 系统属性获取，再从环境变量获取。 
 * - 可复用性高：通过工具类封装了常用的属性读取逻辑。 
 * 总结：这是一个简单而实用的工具类，用于统一管理系统属性和环境变量的读取逻辑。 
**/
package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

public class SystemPropUtil {

	private SystemPropUtil() {}

	/**
	 * 读取环境变量数据。
	 * 优先从jvm变量取，如果取不到，再从环境变量获取
	 * @param key
	 * @return
	 */
	public static String getProp(String key) {
		// 1、先从jvm获取变量
		String value = System.getProperty(key);

		// 2、取不到，再从环境变量获取
		if (StringUtils.isBlank(value)) {
			value = System.getenv(key);
		}

		return value;
	}
}
