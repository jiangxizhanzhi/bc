package com.cyberpeace.common.util;

public interface BaseConvertor<SOURCE, TARGET> {
    TARGET from(SOURCE source);
    SOURCE to(TARGET target);
}
