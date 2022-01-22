package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.constant.WebConstant;
import lombok.Getter;

public enum LogContent {
    /**
     * 操作日志内容描述前缀
     */

    CR_FORMAT("%s %s [%s]", "%s%s[%s]"),
    COURSE_FORMAT("Name of course: %s, Name of lesson: %s.", "课程名：%s，课时名：%s。"),
    PRACTICE_FORMAT("Exercise: %s, the user answer is %s.", "实操题：%s，用户答案：%s。"),
    THEORY_FORMAT("Theory: %s, the user answer is %s.", "理论题：%s，用户答案：%s。"),
    EXERCISE("Exercise", "实操题"),
    THEORY("Theory", "理论题");

    @Getter
    private String enDesc;

    @Getter
    private String zhDesc;

    LogContent(String enDesc, String zhDesc) {
        this.enDesc = enDesc;
        this.zhDesc = zhDesc;
    }

    public static String getLocaleValue(LogContent logLocale, String language) {
        if (language.equals(WebConstant.LANGUAGE_ZH)) {
            return logLocale.zhDesc;
        } else {
            return logLocale.enDesc;
        }
    }

}

