package com.cyberpeace.constant;

import java.util.Formattable;
import java.util.Formatter;

import com.cyberpeace.common.model.CommonContext;

/**
 * 国际化字符串接口
 * enum实现该接口则具备getMsg方法以获取当前上下文语言的国际化字符串
 *
 * @author shazhichuan@see Formattable
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface BaseI18n extends Formattable {

	/**
	 * 获取国际化字符串的默认方法
	 *
	 * @return 国际化字符串
	 * @throws
	 * @see String
	 */
	default String getMsg() {
		return CommonContext.isChinese() ? getChinese() : getEnglish();
	}

	/**
	 * 返回中文字符串
	 *
	 * @return 中文字符串
	 * @throws
	 * @see String
	 */
	String getChinese();

	/**
	 * 返回英文字符串
	 *
	 * @return 英文字符串
	 * @throws
	 * @see String
	 */
	String getEnglish();

	/**
	 * format 设置为
	 *
	 * @param formatter 格式化程序
	 * @param flags     标志
	 * @param width     宽度
	 * @param precision 精度
	 * @throws
	 * @see Formatter
	 * @see int
	 * @see int
	 * @see int
	 */
	@Override
	default void formatTo(Formatter formatter, int flags, int width, int precision) {
		formatter.format(getMsg());
	}

	/**
	 * 提供转化为参数化的字符串的方法
	 *
	 * @param args 参数
	 * @return {@code String }
	 * @throws
	 * @see Object...
	 * @see String
	 */
	default String format(Object... args) {
		return String.format(getMsg(), args);
	}
}
