package com.cyberpeace.common.exception;

/**
 * 用户输入参数异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个自定义异常类 `InvalidParamException`，继承自 Java 的 `RuntimeException`。以下是简要分析： 
 * 1. **功能描述**： 
 * - `InvalidParamException` 是一个运行时异常，用于表示用户输入参数无效的情况。 
 * - 它扩展了 `RuntimeException`，因此不需要显式声明在方法签名中（除非需要明确抛出）。 
 * 2. **构造函数**： 
 * - 提供了一个接受字符串参数 `message` 的构造函数，用于传递异常的具体信息。 
 * 3. **用途**： 
 * - 可以在程序中捕获并处理用户输入错误，例如非法参数值或格式问题。 
 * 4. **扩展性**： 
 * - 通过继承 `RuntimeException`，该类可以灵活地与其他异常机制结合使用。 
 * 总结：这是一个简单的自定义运行时异常类，适用于处理与用户输入相关的错误场景。 
**/
public class InvalidParamException extends RuntimeException {

	public InvalidParamException(String message) {
		super(message);
	}
}

