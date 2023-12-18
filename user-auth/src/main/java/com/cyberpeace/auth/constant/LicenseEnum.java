package com.cyberpeace.auth.constant;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * license 授权枚举类
 */
public enum LicenseEnum implements GenericEnum {

    SUCCESS(0, "成功"),
    LICENSE_FAIL(1, "失败"),
    LICENSE_INVALID(2, "无效"),
    LICENSE_EXPIRED(3, "已过期"),
    LICENSE_NOT_ACTIVATED(4, "未激活");

    @Getter
    public Integer code;

    @Getter
    private String desc;

    private LicenseEnum(int code, String message) {
        this.code = code;
        this.desc = message;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(String.valueOf(code), desc);
        return jsonObject.toJSONString();
    }
}
