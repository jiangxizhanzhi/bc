package com.cyberpeace.common.mdparser.parser;

/**
 * 医学内容解析器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface MdContentParser<T, R> {

	T parse(Object content);

	R write(T t);

}
