package com.cyberpeace.auth.constant;

public enum OpLogModuleEnum {
    USER(1,"用户管理"),
    THEORY(2,"理论题"),
    OPERATION(3,"实操题"),
    LESSON(3,"课时"),
    ;
    Integer type;
    String desc;

    OpLogModuleEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
