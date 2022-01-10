package com.cyberpeace.integratation.constant;

import lombok.Getter;

public enum OperationModule {
    /**
     * 操作日志模块
     */

    SYSTEM(0, "系统"),
    TOOL(1, "工具"),
    COURSE(2, "课程"),
    RESOURCE(3, "资源"),
    ACCOUNT(4, "账号"),
    SCENE(5, "场景"),
    RTKS(6, "职业体系"),
    EXAM(7, "考试"),
    EXERCISE(8, "习题集管理"),
    CAPTURE_FLAG(9, "夺旗解题"),
    THEORY(10, "理论基础"),
    PAPER(11, "试卷"),
    TASK(12, "任务"),
    FLOW(13, "流量"),
    PROBE(14, "探针"),
    DEVICE(15, "靶标"),
    RED_VS_BLUE(16, "红蓝对抗"),
    SINGLE_TRAIN(17, "单兵训练"),
    TEAM_TRAIN(18, "团队训练");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    OperationModule(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
