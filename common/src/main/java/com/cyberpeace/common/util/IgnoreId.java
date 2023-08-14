package com.cyberpeace.common.util;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mappings(value = {
        @Mapping(target = "id", ignore = true),
})
public @interface IgnoreId {}
