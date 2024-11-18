package com.cyberpeace.common.mdparser.parser;

/**
 * MD 内容解析器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface MdContentParser<T, R> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code T }
	 * @throws
	 * @see Object
	 * @see T
	 */
	T parse(Object content);

	/**
	 * 写入
	 *
	 * @param t t
	 * @return {@code R }
	 * @throws
	 * @see T
	 * @see R
	 */
	R write(T t);

}
