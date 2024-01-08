package com.cyberpeace.scene.constant.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.ListUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum SceneEnum implements GenericEnum {

    ACTIVE(0, "运行中", "OJ-3"),
    INACTIVE(1, "关闭", ""),
    CREATE(2, "创建中", "1"),
    DELETED(3, "已删除", "OJ-2"),
    ERROR(4, "错误", ""),
    INIT(5, "初始化", "");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    @Getter
    private String name;

    SceneEnum(Integer code, String desc, String name) {
        this.code = code;
        this.desc = desc;
        this.name = name;
    }

    public static List<Integer> getCodes() {
        return Arrays.asList(ACTIVE.getCode(), CREATE.getCode(), ERROR.getCode());
    }
}
