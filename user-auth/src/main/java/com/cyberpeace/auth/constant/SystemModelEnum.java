package com.cyberpeace.auth.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum SystemModelEnum implements GenericEnum {

    MODEL_GN(0, "功能", "GN"),
    MODEL_SY(0, "水印", "SY");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    @Getter
    private String name;

    SystemModelEnum(Integer code, String desc, String name) {
        this.code = code;
        this.desc = desc;
        this.name = name;
    }
}
