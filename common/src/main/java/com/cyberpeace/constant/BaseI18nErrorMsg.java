package com.cyberpeace.constant;

import lombok.Getter;

/**
 * 国际化错误消息接口
 * 相较国际化字符串多一个errorCode
 * 另外提供一个参数化方法以满足国际化字符串中有参数的情形
 *
 * @author shazhichuan@see BaseI18n
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface BaseI18nErrorMsg extends BaseI18n {
	/**
	 * 返回错误码
	 *
	 * @return 错误码字符串
	 * @throws
	 * @see String
	 */
	String getErrorCode();


	/**
	 * 如果中英文消息中有参数，则使用该方法获取一个新建的参数化的实例
	 *
	 * @param args 消息中的参数 个数可以为0 向上没有限制 参数多余需求时多余的参数会被忽略 参数少于需求时报错
	 * @return 参数化的错误消息实例
	 * @throws
	 * @see Object...
	 * @see I18nErrorMsg
	 */
	default I18nErrorMsg parameterize(Object... args) {
		return new I18nErrorMsg(String.format(getChinese(), args), String.format(getEnglish(), args), getErrorCode());
	}

	/**
	 * 判断输入错误消息实例的错误码是否等于当前实例错误码
	 *
	 * @param i18nErrorMsg 另一个错误消息实例
	 * @return 输入错误消息实例的错误码是否等于当前实例错误码
	 * @throws
	 * @see BaseI18nErrorMsg
	 * @see boolean
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
 * @version 1.0.0
 * @date 2024/11/18
 * @see BaseI18nErrorMsg
 * @since 1.0.0
 */
class I18nErrorMsg implements BaseI18nErrorMsg {
	@Getter
	private final String chinese;

	@Getter
	private final String english;

	@Getter
	private final String errorCode;

	/**
	 * i18n错误消息
	 *
	 * @param chinese 中文
	 * @param english 英语
	 * @param errorCode 错误代号
	 * @see String
	 *
	 * @throws
	 */
	public I18nErrorMsg(String chinese, String english, String errorCode) {
		this.chinese = chinese;
		this.english = english;
		this.errorCode = errorCode;
	}
}
