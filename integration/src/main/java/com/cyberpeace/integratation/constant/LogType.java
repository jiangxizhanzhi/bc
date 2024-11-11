package com.cyberpeace.integratation.constant;

import lombok.Getter;

/**
 * 日志类型
 *
 * @author SN
 * @date 2024/11/11
 */
public enum LogType {
    /**
     * 操作日志模块
     */

    SYSTEM(0, "系统日志"),
    /**
     * 用户
     */
    USER(1, "用户日志"),
    /**
     * 场景
     */
    SCENE(2, "场景日志"),
    /**
     * 业务
     */
    BUSINESS(3, "业务日志");

    /**
     * 代号
     */
    @Getter
    private Integer code;

    /**
     * 描述
     */
    @Getter
    private String desc;

    LogType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
