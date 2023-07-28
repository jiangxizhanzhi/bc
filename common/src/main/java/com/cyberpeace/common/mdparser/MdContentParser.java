package com.cyberpeace.common.mdparser;

public interface MdContentParser<T, R> {

	T parse(Object content);

	R write(T t);

}
