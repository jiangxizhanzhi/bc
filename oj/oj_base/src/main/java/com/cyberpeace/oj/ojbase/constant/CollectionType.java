package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum CollectionType implements GenericEnum {

    THEORY(0, "理论基础"),

    @Deprecated
    REAL_VUL(1, "真实漏洞"),

    OPERATION(2, "实践操作"),

    COURSE(3, "课程"),

    LESSON(4, "课时");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    CollectionType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
