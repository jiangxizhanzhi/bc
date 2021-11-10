package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class PracticeCategoryReq {

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "练习类型 0=理论基础 2=实操习题", required = true)
    @NotNull
    private Integer practiceType;

    @ApiModelProperty("所属父级节点")
    private String parentId;

}
