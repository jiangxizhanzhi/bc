/**
 * 这段代码定义了一个自定义异常类 `BusinessException`，用于处理业务逻辑中的异常。以下是代码的主要功能和特点： 
 * 1. **继承关系**：`BusinessException` 继承自 Java 的标准异常类 `RuntimeException`，表明这是一个运行时异常。 
 * 2. **字段**： 
 * - `errorMessage`：存储错误信息，使用 Lombok 的 `@Getter` 注解自动生成 getter 方法。 
 * 3. **构造方法**： 
 * - 提供了三个构造方法： 
 * - `BusinessException(String code)`：通过错误码初始化异常，并调用 `getMessageByLanguage` 方法获取对应语言的错误信息。 
 * - `BusinessException(String code, Object... args)`：支持格式化错误信息，传入参数后动态生成错误消息。 
 * - `BusinessException()`：默认构造方法。 
 * 4. **静态方法 `getMessageByLanguage`**： 
 * - 根据当前上下文的语言 (`CommonContext.getLanguage`) 判断是中文还是英文。 
 * - 调用 `ConfigUtil.getContextProperty` 方法从配置中获取对应语言的错误信息。 
 * - 如果找不到对应的配置值，则返回空字符串。 
 * 5. **依赖**： 
 * - 使用了 `CommonContext` 和 `ConfigUtil` 工具类来获取语言环境和配置信息。 
 * - 需要 `WebConstant` 常量类定义语言相关的常量（如 `.LANGUAGE_EN`）。 
 * 6. **用途**： 
 * - 该异常类适用于业务逻辑中需要抛出特定错误信息的场景，支持多语言处理，便于国际化开发。 
 * 总结：此代码实现了一个灵活的业务异常类，能够根据上下文语言动态加载错误信息，适合在复杂的业务系统中使用。 
**/
package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 业务处理异常
 *
 * @author yinqingrong@date 2024/11/08@see RuntimeException
 * @version 1.0.0
 * @date 2024/12/04
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

	/**
	 * 错误信息
	 *
	 * @see String
	 */
	@Getter
	private String errorMessage;

	public BusinessException(String code) {
		super(code);
		errorMessage = getMessageByLanguage(code);
	}

	public BusinessException(String code, Object... args) {
		super(code);
		errorMessage = String.format(getMessageByLanguage(code), args);
	}

	public BusinessException() {
		super();
	}

	public static String getMessageByLanguage(String code) {
		String ret;
		if (CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN)) {
			ret = ConfigUtil.getContextProperty(code + ".en");
		} else {
			ret = ConfigUtil.getContextProperty(code + ".cn");
		}
		return ret == null ? "" : ret;
	}
}
