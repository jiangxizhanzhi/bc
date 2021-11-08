package com.cyberpeace.oj.course.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum LessonConfigType implements GenericEnum {


    NODE_COURSE(0, "课程"),

    NODE_CHAPTER(1, "章节"),

    NODE_LESSON(2, "课时");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    LessonConfigType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
