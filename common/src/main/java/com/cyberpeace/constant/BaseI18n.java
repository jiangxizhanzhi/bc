package com.cyberpeace.constant;

import java.util.Formattable;
import java.util.Formatter;

import com.cyberpeace.common.model.CommonContext;

/**
 * 国际化字符串接口
 * enum实现该接口则具备getMsg方法以获取当前上下文语言的国际化字符串
 *
 * @author shazhichuan
 */
public interface BaseI18n extends Formattable {
	/**
	 * 获取国际化字符串的默认方法
	 *
	 * @return 国际化字符串
	 */
	default String getMsg() {
		return CommonContext.isChinese() ? getChinese() : getEnglish();
	}

	/**
	 * 返回中文字符串
	 *
	 * @return 中文字符串
	 */
	String getChinese();

	/**
	 * 返回英文字符串
	 *
	 * @return 英文字符串
	 */
	String getEnglish();


	@Override
	default void formatTo(Formatter formatter, int flags, int width, int precision) {
		formatter.format(getMsg());
	}

	/**
	 * 提供转化为参数化的字符串的方法
	 * */
	default String format(Object ...args){
		return String.format(getMsg(), args);
	}
}
