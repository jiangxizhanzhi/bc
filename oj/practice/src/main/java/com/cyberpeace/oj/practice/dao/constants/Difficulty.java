package com.cyberpeace.oj.practice.dao.constants;

import lombok.Getter;

public enum Difficulty {
    /**
     * 初级
     */
    PRIMARY(0),

    /**
     * 中级
     */
    MIDDLE(1),

    /**
     * 高级
     */
    HIGH(2);

    @Getter
    private final Integer value;

    Difficulty(Integer value) {
        this.value = value;
    }
}
