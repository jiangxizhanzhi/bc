package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class SaveCourseCategoryReq implements Serializable {

    @ApiModelProperty("课程分类名称")
    @NotBlank
    private String name;

    @ApiModelProperty("父级分类编号 第一级则不传值")
    private String parentId;

    @ApiModelProperty(value = "课程类型", hidden = true)
    private Integer courseType;

}
