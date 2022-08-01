package com.cyberpeace.common.mdparser.parser;

public interface MdContentParser<T> {

	T parse(String content);

}
