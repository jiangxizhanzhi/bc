package com.cyberpeace.oj.course.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum CoursePublic implements GenericEnum {

    PUBLIC(1, "公开"), HIDDEN(0, "隐藏");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    private CoursePublic(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
