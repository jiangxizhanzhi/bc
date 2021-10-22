package com.cyberpeace.oj.course.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum CourseDifficulty implements GenericEnum {

    PRIMARY(0, "初级"), MIDDLE(1, "中级"), HIGH(2, "高级");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    private CourseDifficulty(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
