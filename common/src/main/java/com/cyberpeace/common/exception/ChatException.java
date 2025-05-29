package com.cyberpeace.common.exception;

/**
 * 聊天异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BusinessException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ChatException` 的自定义异常类，继承自 `BusinessException`。以下是简要分析： 
 * 1. **类的功能**： 
 * - `ChatException` 是一个专门用于处理与聊天相关的业务异常的类。 
 * - 它扩展了 `BusinessException`，表明它属于业务异常的范畴。 
 * 2. **构造方法**： 
 * - 提供了一个接收 `String` 类型参数的构造方法，用于设置异常信息。 
 * - 通过调用父类的构造方法 `super(message)`，将异常信息传递给 `BusinessException`。 
 * 3. **用途**： 
 * - 该类可以被用于在聊天功能相关的代码中抛出特定的异常，便于统一管理和处理。 
 * 4. **继承关系**： 
 * - 从注释中可以看出，`ChatException` 和 `BusinessException` 都是相关联的类，`ChatException` 是其子类。 
 * 总结：这是一个简单的自定义异常类，用于封装聊天功能中的业务异常，便于代码中抛出和捕获特定异常。 
**/
public class ChatException extends BusinessException {

	public ChatException(String message) {
		super(message);
	}
}

