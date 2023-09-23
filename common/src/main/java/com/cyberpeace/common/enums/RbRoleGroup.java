package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum RbRoleGroup implements GenericEnum, BaseI18n, RoleGroup {
    /***/
    ADMIN(1, "管理员", "Administrator"),
    DIRECTOR(2, "导演", "Director"),
    REFEREE(3, "裁判", "Referee"),
    RED(4, "红方", "Red"),
    BLUE(5, "蓝方", "Blue"),
    RED_BLUE(6, "红方蓝方", "RedBlue"),
    WHITE(7, "白方", "White"),
    TEAM(8, "团队", "Team"),
    RED_GROUP(4, "红方群", "Red Group"),
    BLUE_GROUP(5, "蓝方群", "Blue Group"),
    WHITE_GROUP(7, "白方群", "White Group"),
    TEAM_GROUP(8, "团队群", "Team Group"),
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String chinese;

    @Getter
    private final String english;

    @Override
    public String getDesc() {
        return getMsg();
    }

    RbRoleGroup(Integer code, String chinese, String english) {
        this.code = code;
        this.chinese = chinese;
        this.english = english;
    }

    public boolean matches(Integer roleGroupCode) {
        return getCode().equals(roleGroupCode);
    }

    public static boolean isRvbStudentRole(Integer roleGroupCode) {
        return RED.matches(roleGroupCode) || BLUE.matches(roleGroupCode);
    }

    public static List<RbRoleGroup> studentRoleGroups() {
        ArrayList<RbRoleGroup> rbRoleGroups = Lists.newArrayList();
        rbRoleGroups.add(RED);
        rbRoleGroups.add(BLUE);
        return rbRoleGroups;
    }
}
