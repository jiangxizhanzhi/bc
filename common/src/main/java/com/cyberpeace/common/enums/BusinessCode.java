package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

public enum BusinessCode implements GenericEnum, BaseI18n {
    /***/
    DEFAULT(0, "默认"),
    PRACTICE(110, "练习"),
    LESSON(120, "课程"),
    CLASSROOM(121, "课程表"),
    EXAM(130, "考试"),
    LESSON_PRACTICE(140, "课程练习"),
    SINGLE_TRAINING(211, "单兵训练", "Individual Training"),
    LIVE_VULN(212, "漏洞"),
    TEAM_TRAINING(213, "团队训练", "Team Training"),
    RED_VS_BLUE(214, "红蓝对抗", "Cyber Wargame"),
    WEAPON_TEST(216, "武器"),
    SYSTEM_TEST(217, "系统"),
    SOFTWARE_TEST(221, "软件测试"),
    TEST_SCENE(222, "后台测试"),
    COURSE_TRAINING(150, "课程训练"),
    SKILLS_ASSESSMENT(231, "技能评估"),
    TACTICAL_ASSESSMENT(232, " 战术评估"),
    COMPREHENSIVE_ASSESSMENT(233, "综合评估"),
    TACTICAL_TRAINING(234, "战术训练"),
    COMPREHENSIVE_TRAINING(235, "综合训练"),
    RANGE_EVALUATION(236, "靶场测评");

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

    BusinessCode(Integer code, String chinese) {
        this.code = code;
        this.chinese = chinese;
        this.english = this.name();
    }

    BusinessCode(Integer code, String chinese, String english) {
        this.code = code;
        this.chinese = chinese;
        this.english = english;
    }

}
