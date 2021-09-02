package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PracticeCategoryReq {

    @ApiModelProperty(hidden = true)
    private String resourceId;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "练习类型", required = true)
    private Integer practiceType;

}
