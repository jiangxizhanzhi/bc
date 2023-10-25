package com.cyberpeace.common.util;

public interface BaseConvertor<S, T> {
    T from(S source);
    S to(T target);
}
