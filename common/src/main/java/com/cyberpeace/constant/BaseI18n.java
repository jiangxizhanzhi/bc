/**
 * 这段代码定义了一个用于国际化（i18n）的接口 `BaseI18n`。以下是其主要功能和结构的简要分析： 
 * 1. **接口定义**： 
 * - 接口 `BaseI18n` 继承了 `java.util.Formattable`，这使得实现了该接口的对象可以使用 `Formatter` 进行格式化输出。 
 * 2. **核心方法**： 
 * - `getMsg()`：默认方法，根据当前上下文语言环境（通过 `CommonContext.isChinese()` 判断）返回对应的中文或英文字符串。 
 * - `getChinese()` 和 `getEnglish()`：分别返回中文和英文的国际化字符串。 
 * 3. **格式化支持**： 
 * - 重写了 `formatTo` 方法，允许通过 `Formatter` 格式化输出 `getMsg()` 的结果。 
 * 4. **参数化字符串**： 
 * - 提供了 `format(Object... args)` 方法，利用 `String.format` 实现参数化字符串的生成。 
 * 总结：该接口为实现国际化提供了基础框架，支持多语言切换、格式化输出以及参数化字符串，便于在不同语言环境下统一管理字符串资源。 
**/
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
	default String format(Object... args) {
		return String.format(getMsg(), args);
	}
}
