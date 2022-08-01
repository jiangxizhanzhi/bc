package com.cyberpeace.common.mdparser.annotation;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
import com.cyberpeace.common.mdparser.convert.NoConvert;
import com.cyberpeace.common.mdparser.parser.StringParser;

import java.lang.annotation.*;

/**
 * @author chenzhuo
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MdHeadLine {

	/**
	 * 标题等级 1=# 2=##
	 * @return
	 */
	int level() default -1;

	/**
	 * 要匹配的标题名称
	 * @return
	 */
	String name() default "";

	/**
	 * 使用哪种格式化
	 * @return
	 */
	Class parser() default StringParser.class;

	/**
	 * 非精确匹配
	 * @return
	 */
	boolean startWith() default false;

	/**
	 * 转换器 将获取到的值 通过枚举进行转换 通过to 获取 reTo
	 * @return
	 */
	Class<? extends MdConvertEnum> convert() default NoConvert.class;

	/**
	 * 反转器 将获取到的值 通过枚举进行转换 通过reTo 获取 to
	 * @return
	 */
	Class<? extends MdConvertEnum> reConvert() default NoConvert.class;
}
