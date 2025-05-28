/**
 * 这段代码定义了一个自定义异常类 `JwtTokenInvalidException`，继承自 Java 的标准运行时异常类 `RuntimeException`。以下是简要分析： 
 * 1. **功能**： 
 * - 该类用于表示 JWT（JSON Web Token）令牌无效的异常情况。 
 * - 它提供了一种结构化的方式来抛出和处理 JWT 相关的异常。 
 * 2. **继承关系**： 
 * - 继承自 `RuntimeException`，表明这是一个运行时异常，通常不需要强制捕获（unchecked exception）。 
 * 3. **构造方法**： 
 * - 提供了两个构造方法： 
 * - 带参数的构造方法：接受一个字符串消息作为异常信息。 
 * - 无参构造方法：默认使用父类 `RuntimeException` 的构造逻辑。 
 * 4. **用途**： 
 * - 在处理 JWT 验证逻辑时，如果发现令牌无效，可以抛出此异常，便于上层调用者统一处理。 
 * 5. **注释**： 
 * - 类上有详细的 Javadoc 注释，说明了异常的用途、作者、版本等信息，符合良好的编码规范。 
 * 总结：这是一个简洁且功能明确的自定义异常类，适用于 JWT 相关的异常处理场景。 
**/
package com.cyberpeace.common.exception.evaluate;

/**
 * JWT 令牌无效异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
public class JwtTokenInvalidException extends RuntimeException {

	public JwtTokenInvalidException(String message) {
		super(message);
	}

	public JwtTokenInvalidException() {
		super();
	}
}
