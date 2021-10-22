package com.cyberpeace.oj.course.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum LessonType implements GenericEnum {

    THEORY(0, "理论课"), EXPERIMENT(1, "实验课");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    private LessonType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
