package com.cyberpeace.oj.practice.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum Auth implements GenericEnum {
    /**
     * 授权所有
     */
    ALL(1, "共享"),

    /**
     * 自定义授权
     */
    CUSTOM(2, "自定义");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    Auth(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
