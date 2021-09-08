package com.cyberpeace.oj.practice.dao.constants;

import lombok.Getter;

public enum PracticeType {
    /**
     * 理论基础
     */
    THEORY(0),

    /**
     * 真实漏洞 (已弃用, 整合至 实践操作-OPERATION)
     */
    @Deprecated
    REAL_VUL(1),

    /**
     * 实践操作
     */
    OPERATION(2);

    @Getter
    private final Integer value;

    PracticeType(Integer value) {
        this.value = value;
    }
}
