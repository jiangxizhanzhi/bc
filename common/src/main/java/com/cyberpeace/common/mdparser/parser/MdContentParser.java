package com.cyberpeace.common.mdparser.parser;

public interface MdContentParser<T, R> {

	T parse(Object content);

	R write(T t);

}
