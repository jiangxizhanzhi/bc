package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

public enum TeamRoleGroup implements GenericEnum, RoleGroup, BaseI18n {

    /***/
    TEACHER(1, "教员", "Teacher"),
    STUDENT(2, "学员", "Student");

    @Getter
    private final Integer code;

    @Getter
    private final String chinese;

    @Getter
    private final String english;

    TeamRoleGroup(Integer code, String chinese, String english) {
        this.code = code;
        this.chinese = chinese;
        this.english = english;
    }

    public boolean equals(Integer roleGroupCode) {
        return getCode().equals(roleGroupCode);
    }

    @Override
    public String getChinese() {
        return this.chinese;
    }

    @Override
    public String getEnglish() {
        return this.english;
    }

    @Override
    public String getDesc() {
        return this.getMsg();
    }
}
