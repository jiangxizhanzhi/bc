package com.cyberpeace.oj.practice.dao.constants;

import lombok.Getter;

public enum Share {

    /**
     * 授权所有
     */

    ALL(1),

    /**
     * 自定义授权
     */
    CUSTOM(2);

    @Getter
    private final Integer value;

    Share(Integer value) {
        this.value = value;
    }
}
