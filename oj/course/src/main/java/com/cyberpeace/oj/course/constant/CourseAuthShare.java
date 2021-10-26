package com.cyberpeace.oj.course.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum CourseAuthShare implements GenericEnum {

    ALL(1, "共享"), CUSTOM(2, "自定义");

    @Getter
    public Integer code;
    @Getter
    public String desc;

    private CourseAuthShare(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
