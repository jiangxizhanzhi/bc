package com.cyberpeace.common.exception;

/**
 * 远程Service例外
 *
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常@date 2024/11/08@see RuntimeException
 * @version 1.0.0
 * @date 2024/12/04
 * @see RuntimeException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个自定义异常类 `RemoteServiceException`，继承自 Java 标准库中的 `RuntimeException`。以下是对其的简要分析： 
 * ### 功能描述 
 * - **用途**：用于表示远程服务调用失败时抛出的异常。 
 * - **继承关系**：该类继承自 `RuntimeException`，属于运行时异常，因此调用方不需要显式捕获。 
 * ### 构造方法 
 * 1. **带参构造方法**： 
 * ```java 
 * public RemoteServiceException(String message) 
 * ``` 
 * - 参数 `message`：用于传递异常的具体信息。 
 * - 调用了父类 `RuntimeException` 的构造方法，将消息传递给它。 
 * 2. **无参构造方法**： 
 * ```java 
 * public RemoteServiceException() 
 * ``` 
 * - 提供了默认的无参构造方法，方便在没有额外信息时使用。 
 * ### 注释说明 
 * - 注释清晰地描述了异常的用途（远程服务调用失败）及继承的父类（`RuntimeException`）。 
 * - 提供了作者、创建日期、版本等元信息，符合良好的注释规范。 
 * ### 总结 
 * 这是一个简单但功能明确的自定义异常类，适用于处理远程服务调用相关的错误场景。通过继承 `RuntimeException`，它避免了强制要求调用方显式捕获，提升了代码的灵活性。 
**/
public class RemoteServiceException extends RuntimeException {

	public RemoteServiceException(String message) {
		super(message);
	}

	public RemoteServiceException() {}
}

