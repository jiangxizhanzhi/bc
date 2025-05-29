package com.cyberpeace.common.exception;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

/**
 * i18n 异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `I18nException` 的自定义异常类，继承自 Java 的标准异常类 `RuntimeException`。以下是代码的简要分析： 
 * 1. **类的用途**： 
 * - `I18nException` 是一个用于国际化（i18n）场景的自定义运行时异常。 
 * - 它允许在抛出异常时携带与国际化相关的错误信息。 
 * 2. **关键特性**： 
 * - **继承关系**：继承自 `RuntimeException`，表示这是一个运行时异常，调用方无需显式捕获。 
 * - **字段**：包含一个私有只读字段 `i18nErrorMsg`，类型为 `BaseI18nErrorMsg`，用于存储国际化错误消息。 
 * - **Lombok 注解**：使用了 Lombok 的 `@Getter` 注解，自动生成了 `i18nErrorMsg` 的 getter 方法。 
 * - **构造方法**：提供了一个构造方法，接受一个 `BaseI18nErrorMsg` 参数并将其赋值给字段。 
 * 3. **依赖**： 
 * - 依赖于 `com.cyberpeace.constant.BaseI18nErrorMsg` 类，该类可能是存储国际化错误消息的枚举或常量类。 
 * 4. **适用场景**： 
 * - 适用于需要在系统中传递和处理国际化错误信息的场景，特别是在多语言支持的应用中。 
 * 总结：`I18nException` 是一个简洁且实用的自定义异常类，专门用于处理国际化相关的错误信息，便于在代码中抛出和捕获特定的国际化异常。 
**/
public class I18nException extends RuntimeException {
	/**
	 * i18n 错误 msg
	 *
	 * @see BaseI18nErrorMsg
	 */
	@Getter
	private final BaseI18nErrorMsg i18nErrorMsg;

	public I18nException(BaseI18nErrorMsg i18nErrorMsg) {
		this.i18nErrorMsg = i18nErrorMsg;
	}
}

