package com.cyberpeace.scene.constant.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum ImageType implements GenericEnum {

    IMAGE_TYPE_HOST(0, "主机"),
    IMAGE_TYPE_CONTAINER(1, "容器");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ImageType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
