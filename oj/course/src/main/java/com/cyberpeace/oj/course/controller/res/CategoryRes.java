package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class CategoryRes implements Serializable {

    @ApiModelProperty("资源编号")
    private String resourceId;

    @ApiModelProperty("课程分类名称")
    private String name;

    @ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
    private Integer courseType;

}
