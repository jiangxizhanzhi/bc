package com.cyberpeace.cr.crbase.controller.rsp.guide;

import lombok.Data;

/**
 * 参数类型响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class ParamTypeRsp {
    /**
     * 名称
     */
    private String name;
    /**
     * 代号
     */
    private Integer code;

    public ParamTypeRsp(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}