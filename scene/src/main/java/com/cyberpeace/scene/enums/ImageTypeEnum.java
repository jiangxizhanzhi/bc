package com.cyberpeace.scene.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public enum ImageTypeEnum {

    VM,

    DYNAMIPS,

    CONTAINER,

    ENTITY,

    SIMPLE;


    ImageTypeEnum() {

    }

    private static Map<String, ImageTypeEnum> map = new ConcurrentHashMap<>();

    static {
        Arrays.stream(ImageTypeEnum.values()).forEach(
                t -> map.put(t.name().toLowerCase(Locale.ROOT), t)
        );
    }

    @JsonCreator
    public static ImageTypeEnum fromValue(String desc) {
        return map.get(desc);
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
