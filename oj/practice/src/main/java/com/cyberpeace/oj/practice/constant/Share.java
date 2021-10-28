package com.cyberpeace.oj.practice.constant;

import lombok.Getter;

public enum Share {

    ALL(1, "共享"),

    CUSTOM(2, "自定义");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    Share(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
