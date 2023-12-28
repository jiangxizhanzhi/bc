package com.cyberpeace.scene.constant.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum ImageTypeEnum implements GenericEnum {

    ELEMENT_TYPE_INTERNET(1, "网络", "Internet"),
    ELEMENT_TYPE_HOST(4, "主机", "host"),
    ELEMENT_TYPE_CONTAINER(5, "容器", "container");


    @Getter
    private Integer code;

    @Getter
    private String desc;

    @Getter
    private String name;

    ImageTypeEnum(Integer code, String desc, String name) {
        this.code = code;
        this.desc = desc;
        this.name = name;
    }
}
