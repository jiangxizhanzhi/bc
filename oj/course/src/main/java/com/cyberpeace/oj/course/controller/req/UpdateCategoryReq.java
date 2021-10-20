package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class UpdateCategoryReq extends SaveCourseCategoryReq {

    @ApiModelProperty("资源编号")
    @NotBlank
    private String resourceId;

}
