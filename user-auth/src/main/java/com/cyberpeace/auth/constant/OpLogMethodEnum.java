package com.cyberpeace.auth.constant;

public enum OpLogMethodEnum {
    STUDY(1,"学习"),
    START(2,"启动"),
    STOP(3,"停止"),
    PRACTICE(4,"练习"),
    LOGIN(5,"登录"),
    LOGOUT(6,"登出"),
    PAPER(7,"组卷"),
    MOCK(8,"模拟"),
    MOCK_CONTINUE(9,"继续模拟"),
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
