package com.cyberpeace.cr.crbase.controller.rsp.guide;

import lombok.Data;

@Data
public class ParamTypeRsp {
    private String name;
    private Integer code;

    public ParamTypeRsp(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}