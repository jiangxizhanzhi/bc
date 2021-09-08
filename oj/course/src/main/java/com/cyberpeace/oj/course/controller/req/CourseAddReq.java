package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程新增信息")
public class CourseAddReq implements Serializable {

    @ApiModelProperty("课程编号")
    private String resourceId;

    @ApiModelProperty("是否为系统内置")
    @NotNull
    private Boolean builtin;

    @NotNull
    @ApiModelProperty("授权访问类型 1=全部 2=自定义")
    private Integer auth;

    @ApiModelProperty("授权访问组织")
    private String authOrganization;

    @NotNull
    @ApiModelProperty("授权操作类型 1=全部 2=自定义")
    private Integer share;

    @ApiModelProperty("被授权人")
    private String shareTeachers;

    @NotBlank
    private String name;

    private String description;

    @ApiModelProperty("课程方向")
    @NotBlank
    private String direction;

    @NotBlank
    private String logo;

    private String courseWriteup;

    @NotNull
    private Integer difficulty;

    @ApiModelProperty("是否公开")
    @NotNull
    private Boolean isPublic;

    @NotNull
    private Integer eBook;

    @ApiModelProperty("讲师")
    private String lecturer;

    @ApiModelProperty("学分")
    private Integer score;

}
