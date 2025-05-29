package com.cyberpeace.common.exception;

/**
 * 数据库对应资源失败，抛出此异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个自定义异常类 `ResourceNotFoundException`，继承自 Java 的标准运行时异常类 `RuntimeException`。以下是简要分析： 
 * ### 功能分析： 
 * - **用途**：该异常用于表示数据库中指定资源未找到的情况。 
 * - **构造方法**：提供一个接受字符串参数的构造方法，用于设置异常信息。 
 * ### 特点： 
 * 1. **继承关系**：继承自 `RuntimeException`，表明这是一个运行时异常，通常不需要强制捕获。 
 * 2. **构造方法**：通过调用父类构造方法，将传入的消息设置为异常描述信息。 
 * 3. **注释说明**：注释清晰地描述了异常的用途、使用场景以及参数含义。 
 * ### 使用场景： 
 * 当在数据库操作中无法找到指定资源时，可以抛出此异常，并传入相关字段（如 `resource_id`）作为消息，便于定位问题。 
 * 总结：这是一个简单的、针对特定业务场景设计的运行时异常类。 
**/
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * @param message 假设根据resource_id字段查询数据失败，message=resource_id；
	 *                多个字段联合查询时，此字段为空
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}

