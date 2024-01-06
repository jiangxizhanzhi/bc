package com.cyberpeace.scene.constant.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * @author :   辛发圣
 * @date :   2024/1/4 10:20
 * @desc :
 **/
public enum StatusEnum implements GenericEnum {

    ACTIVE(2, "运行中", "Running"),
    CREATING(1, "创建中", "Creating"),
    FINISHED(3, "已完成", "Finished"),
    ERROR(4, "错误", "Error"),
    NETWORK_ACTIVE(2, "运行中", "ACTIVE");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    @Getter
    private String name;

    StatusEnum(Integer code, String desc, String name) {
        this.code = code;
        this.desc = desc;
        this.name = name;
    }

    public static Integer getCode(String name) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.name.equals(name)) {
                return statusEnum.code;
            }
        }
        return 4;
    }
}
