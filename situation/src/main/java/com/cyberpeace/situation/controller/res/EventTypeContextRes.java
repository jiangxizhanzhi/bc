package com.cyberpeace.situation.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 30/11/2022 下午5:39
 */
@Data
public class EventTypeContextRes implements Serializable {
    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    public EventTypeContextRes() {}

    public EventTypeContextRes(String code, String name) {
        this.name = name;
        this.code = code;
    }
}
