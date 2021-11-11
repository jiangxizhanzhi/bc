package com.cyberpeace.oj.practice.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum PracticeType implements GenericEnum {

    THEORY(0, "理论基础"),

    /** 真实漏洞 (已弃用, 整合至 实践操作-OPERATION)*/
    @Deprecated
    REAL_VUL(1, "真实漏洞"),

    OPERATION(2, "实践操作");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    PracticeType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
