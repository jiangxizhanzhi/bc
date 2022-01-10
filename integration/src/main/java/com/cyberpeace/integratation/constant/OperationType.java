package com.cyberpeace.integratation.constant;

import lombok.Getter;

public enum OperationType {
    /**
     * 操作类型
     */

    LOGIN(0, "登入"),
    SIGN_OUT(1, "登出"),
    CREATE(2, "创建"),
    UPDATE(3, "更新"),
    DELETE(4, "删除"),
    STUDY(5, "学习"),
    EXERCISE(6, "练习"),
    EXAM(7, "考试"),
    TRAIN(8, "训练"),
    TEST(9, "测评"),
    PARTICIPATE(10, "参赛"),
    STOP(11, "停止"),
    PAUSE(12, "暂停"),
    START(13, "启动"),
    UNPAUSE(14, "恢复");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    OperationType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
