package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("后台课程信息")
public class CourseAdminBo extends CourseWebBo {

    @ApiModelProperty("授权访问类型")
    private Integer auth;

    @ApiModelProperty("授权访问数量")
    private Integer authCount;

    @ApiModelProperty("授权操作类型")
    private Integer share;

    @ApiModelProperty("授权操作数量")
    private Integer shareCount;
}
