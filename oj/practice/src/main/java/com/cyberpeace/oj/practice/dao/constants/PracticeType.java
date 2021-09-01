package com.cyberpeace.oj.practice.dao.constants;

public enum PracticeType {
    /**
     * 理论基础
     */
    THEORY,

    /**
     * 真实漏洞 (已弃用, 整合至 实践操作-OPERATION)
     */
    @Deprecated
    REAL_VUL,

    /**
     * 实践操作
     */
    OPERATION
}
