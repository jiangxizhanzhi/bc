package com.cyberpeace.oj.practice.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum PaperType implements GenericEnum {

    LIB(1, "试卷库"),

    EXAM(2, "考试"),

    LESSON(3, "课后练习");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    PaperType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
