package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class DirectionOutPut implements Serializable {

    @ApiModelProperty("分类编号")
    private String resourceId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("子类分类")
    private List<DirectionOutPut> children;

}
