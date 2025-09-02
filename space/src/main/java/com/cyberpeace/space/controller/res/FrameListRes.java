package com.cyberpeace.space.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FrameListRes implements Serializable {

    @ApiModelProperty("框架id")
    private String frameId;

    @ApiModelProperty("框架名称")
    private String name;

    @ApiModelProperty("目标类型")
    private String categoryId;

    @ApiModelProperty("应用场景")
    private List<String> tag;

    @ApiModelProperty("是否选择")
    private Boolean choose;
}
