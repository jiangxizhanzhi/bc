package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum AuthShare implements GenericEnum {

    ALL(1, "共享"), CUSTOM(2, "自定义");

    // xml中被引用 能改为private
    @Getter
    public Integer code;
    @Getter
    public String desc;

    private AuthShare(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
