package com.cyberpeace.integratation.constant;

import lombok.Getter;

public enum LogType {
    /**
     * 操作日志模块
     */

    SYSTEM(0, "系统日志"),
    USER(1, "用户日志"),
    SCENE(2, "场景日志"),
    BUSINESS(3, "业务日志");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    LogType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
