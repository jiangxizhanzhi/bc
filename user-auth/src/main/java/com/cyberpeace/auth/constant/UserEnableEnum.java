package com.cyberpeace.auth.constant;

/**
 * 用户是否禁用枚举
 */
public enum UserEnableEnum {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    Integer code;

    String message;

    UserEnableEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
