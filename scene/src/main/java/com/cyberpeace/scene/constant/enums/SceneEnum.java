package com.cyberpeace.scene.constant.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum SceneEnum implements GenericEnum {

    ACTIVE(0, "运行中"),
    INACTIVE(1, "关闭");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    SceneEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
