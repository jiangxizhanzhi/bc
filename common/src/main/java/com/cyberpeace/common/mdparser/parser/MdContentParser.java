package com.cyberpeace.common.mdparser.parser;

/**
 * MD 内容解析器
 *
 * @author SN
 * @date 2024/11/07
 */
public interface MdContentParser<T, R> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code T }
	 */
	T parse(Object content);

	/**
	 * 写入
	 *
	 * @param t t
	 * @return {@code R }
	 */
	R write(T t);

}
