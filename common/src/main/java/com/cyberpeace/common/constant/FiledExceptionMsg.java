package com.cyberpeace.common.constant;

import com.cyberpeace.constant.BaseI18nErrorMsg;

import lombok.Getter;

public enum FiledExceptionMsg implements BaseI18nErrorMsg {

    /**
     * 字段校验枚举
     */
    COURSE_ID("course_id", "课程信息异常", "Course information exception."),
    LESSON_ID("lesson_id", "课时信息异常", "Lesson information exception."),
    ORGANIZATION_ID("organization_id", "组织信息异常", "Organization information exception."),
    START_TIME("start_time", "开始时间异常", "Start time information exception."),
    END_TIME("end_time", "结束时间异常", "End time information exception.");

    @Getter
    private final String chinese;

    @Getter
    private final String english;

    @Getter
    private final String errorCode;

    FiledExceptionMsg(String errorCode, String chinese, String english) {
        this.errorCode = errorCode;
        this.chinese = chinese;
        this.english = english;
    }

}
