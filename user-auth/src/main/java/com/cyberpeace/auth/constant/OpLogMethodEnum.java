package com.cyberpeace.auth.constant;

public enum OpLogMethodEnum {
    CREATE(1,"新增"),
    DELETE(2,"删除"),
    UPDATE(3,"修改"),
    LOGIN(4,"登录"),
    LOGOUT(5,"登出"),
    ;
    Integer type;
    String desc;

    OpLogMethodEnum(Integer type, String desc) {
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
