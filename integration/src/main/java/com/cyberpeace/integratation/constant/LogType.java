package com.cyberpeace.integratation.constant;

import lombok.Getter;

/**
 * 日志类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Enum
 * @since 1.0.0
 */
public enum LogType {
    /**
     * 操作日志模块
     *
     * @see LogType
     */

    SYSTEM(0, "系统日志"),
    /**
     * 用户
     *
     * @see LogType
     */
    USER(1, "用户日志"),
    /**
     * 场景
     *
     * @see LogType
     */
    SCENE(2, "场景日志"),
    /**
     * 业务
     *
     * @see LogType
     */
    BUSINESS(3, "业务日志");

    /**
     * 代号
     *
     * @see Integer
     */
    @Getter
    private Integer code;

    /**
     * 描述
     *
     * @see String
     */
    @Getter
    private String desc;

    LogType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
