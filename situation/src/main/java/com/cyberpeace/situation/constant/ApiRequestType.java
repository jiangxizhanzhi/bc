package com.cyberpeace.situation.constant;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * @Author xuxz
 * @Date 4/11/2022 上午11:11
 */
public enum ApiRequestType {
    POST(1, "POST"),
    DELETE(2, "DELETE"),
    PATCH(3, "PATCH"),
    GET(4, "GET");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ApiRequestType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<String> getRequestType() {
        return Lists.newArrayList(POST.desc, DELETE.desc, PATCH.desc, GET.desc);
    }
}
