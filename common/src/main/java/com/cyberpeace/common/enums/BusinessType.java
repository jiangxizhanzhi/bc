package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum BusinessType implements GenericEnum {

    TRAINING_SINGLE(1, "单兵训练"), VULN_VALIDATE(2, "漏洞库"), TRAINING_TEAM(3,
            "团队训练"), RED_VS_BLUE(4, "红蓝对抗"), WEAPON_ASSESS(6, "装备测试"),
    SYSTEM_ASSESS(7, "系统测试"),UNITE_TRAIN(8,"联合训练");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    private BusinessType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
