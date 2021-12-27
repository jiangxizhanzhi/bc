package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum AreaStatus {
    /**
     *区域状态
     */

    NORMAL("normal", "正常");

    @Getter
    private String code;

    @Getter
    private String desc;

    AreaStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
