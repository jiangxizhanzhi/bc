package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.constant.WebConstant;
import lombok.Getter;

public enum OperationModule {
    /**
     * 操作日志模块
     */

    SYSTEM(0, "系统","system"),
    TOOL(1, "工具","tool"),
    COURSE(2, "课程","course"),
    RESOURCE(3, "资源","resource"),
    ACCOUNT(4, "账号","account"),
    SCENE(5, "场景","scene"),
    RTKS(6, "职业体系","rtks"),
    EXAM(7, "考试","exam"),
    EXERCISE(8, "习题集管理","exercise"),
    CAPTURE_FLAG(9, "夺旗解题","practice"),
    THEORY(10, "理论基础","theory"),
    PAPER(11, "试卷","exam paper"),
    TASK(12, "任务","task"),
    FLOW(13, "流量","flow"),
    PROBE(14, "探针","probe"),
    DEVICE(15, "靶标","target"),
    RED_VS_BLUE(16, "红蓝对抗","cyber wargame"),
    SINGLE_TRAIN(17, "单兵训练","individual train"),
    TEAM_TRAIN(18, "团队训练","team train");

    @Getter
    private Integer code;

    @Getter
    private String zhDesc;

    @Getter
    private String enDesc;

    OperationModule(Integer code, String zhDesc, String enDesc) {
        this.code = code;
        this.zhDesc = zhDesc;
        this.enDesc = enDesc;
    }

    public static String getLocaleValue(OperationModule operationModule, String language) {
        if (language.equals(WebConstant.LANGUAGE_ZH)) {
            return operationModule.zhDesc;
        } else {
            return operationModule.enDesc;
        }
    }

}
