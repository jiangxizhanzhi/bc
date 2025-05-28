/**
 * 这段代码定义了一个用于国际化错误消息的接口 `BaseI18nErrorMsg` 和其实现类 `I18nErrorMsg`。以下是简要分析： 
 * ### 接口 `BaseI18nErrorMsg` 
 * - **功能**：提供国际化错误消息的基础接口，包含错误码 (`getErrorCode`) 和支持参数化消息的方法 (`parameterize`)。 
 * - **特点**： 
 * - 提供了获取错误码的接口方法。 
 * - 定义了参数化消息的方法，允许动态替换消息中的占位符。 
 * - 提供了判断两个错误消息是否具有相同错误码的方法 (`isSameErrorCode`)。 
 * ### 实现类 `I18nErrorMsg` 
 * - **功能**：实现了 `BaseI18nErrorMsg` 接口，用于存储具体的中文、英文错误消息及其对应的错误码。 
 * - **特点**： 
 * - 使用 Lombok 的 `@Getter` 注解简化字段的 getter 方法生成。 
 * - 构造函数接受中文、英文消息和错误码，并将其封装为对象。 
 * - 支持通过 `parameterize` 方法创建参数化的错误消息实例。 
 * ### 总结 
 * 这段代码设计了一个灵活的国际化错误消息处理机制，支持错误码和参数化消息，便于在不同语言环境中统一管理和使用错误信息。 
**/
package com.cyberpeace.constant;

import lombok.Getter;

/**
 * 国际化错误消息接口
 * 相较国际化字符串多一个errorCode
 * 另外提供一个参数化方法以满足国际化字符串中有参数的情形
 *
 * @author shazhichuan
 */
public interface BaseI18nErrorMsg extends BaseI18n {
	/**
	 * 返回错误码
	 *
	 * @return 错误码字符串
	 */
	String getErrorCode();


	/**
	 * 如果中英文消息中有参数，则使用该方法获取一个新建的参数化的实例
	 *
	 * @param args 消息中的参数 个数可以为0 向上没有限制 参数多余需求时多余的参数会被忽略 参数少于需求时报错
	 * @return 参数化的错误消息实例
	 */
	default I18nErrorMsg parameterize(Object... args) {
		return new I18nErrorMsg(String.format(getChinese(), args), String.format(getEnglish(), args), getErrorCode());
	}

	/**
	 * 判断输入错误消息实例的错误码是否等于当前实例错误码
	 *
	 * @param i18nErrorMsg 另一个错误消息实例
	 * @return 输入错误消息实例的错误码是否等于当前实例错误码
	 */
	default boolean isSameErrorCode(BaseI18nErrorMsg i18nErrorMsg) {
		return i18nErrorMsg.getErrorCode().equals(getErrorCode());
	}
}

/**
 * 国际化错误消息接口实现类
 * 仅用于字符串带参数的情形
 *
 * @author SN
 * @date 2024/11/08
 */
class I18nErrorMsg implements BaseI18nErrorMsg {
	/**
	 * 中文
	 */
	@Getter
	private final String chinese;

	/**
	 * 英语
	 */
	@Getter
	private final String english;

	/**
	 * 错误代号
	 */
	@Getter
	private final String errorCode;

	/**
	* i18n 错误 msg
	*
	* @param chinese 中文
	* @param english 英语
	* @param errorCode 错误代号
	*/
	public I18nErrorMsg(String chinese, String english, String errorCode) {
		this.chinese = chinese;
		this.english = english;
		this.errorCode = errorCode;
	}
}
