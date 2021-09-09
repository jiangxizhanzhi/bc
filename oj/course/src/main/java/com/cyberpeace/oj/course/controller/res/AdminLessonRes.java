package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程信息")
public class AdminLessonRes implements Serializable {

    @ApiModelProperty("课时编号")
    private String resourceId;

    @ApiModelProperty("课程编号")
    private String course;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("课程类型")
    private Integer type;

    @ApiModelProperty("讲义类型")
    private Integer handoutType;

    @ApiModelProperty("讲义地址")
    private String handoutFile;

    @ApiModelProperty("难度")
    private Integer difficulty;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("学习类型")
    private Integer lessonType;

    @ApiModelProperty("课时时长")
    private Integer duration;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建人姓名")
    private String createUserName;

}
