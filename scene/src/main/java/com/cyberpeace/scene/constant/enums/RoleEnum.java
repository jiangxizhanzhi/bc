package com.cyberpeace.scene.constant.enums;

import lombok.Getter;

/**
 * @author :   SN
 * @date :   2024/1/5 15:42
 * @desc :
 **/
public enum RoleEnum {

    ROLE_ONER(0, 3, "vm"),
    ROLE_ONE(1, 22, "vm"),
    ROLE_TWO(2, 21, "vm"),
    ROLE_THREE(3, 23, "vm"),
    ROLE_FOUR(4, 4, "vm");

    @Getter
    private Integer code;

    @Getter
    private Integer value;

    @Getter
    private String imageType;

    RoleEnum(Integer code, Integer value, String imageType){
        this.code = code;
        this.value = value;
        this.imageType = imageType;
    }

    public static RoleEnum getRoleEnum(Integer code){
        for(RoleEnum roleEnum : RoleEnum.values()){
            if(roleEnum.getCode().equals(code)){
                return roleEnum;
            }
        }
        return null;
    }
}
