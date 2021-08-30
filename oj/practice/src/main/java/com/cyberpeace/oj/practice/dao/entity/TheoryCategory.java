package com.cyberpeace.oj.practice.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("理论习题分类")
public class TheoryCategory implements Serializable {


    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("分类编号")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态")
    private Integer status;

}
