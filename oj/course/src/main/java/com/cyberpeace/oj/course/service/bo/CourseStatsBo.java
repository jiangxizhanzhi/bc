package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseStatsBo implements Serializable {

    @ApiModelProperty("课程resource_id")
    private String resourceId;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("方向名称")
    private String directionName;

    @ApiModelProperty("课程难度")
    private Integer difficulty;

    @ApiModelProperty("报告数量")
    private Integer reportCount;

    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    @ApiModelProperty("学习人数")
    private Integer learningCount;

    @ApiModelProperty("创建用户resource_id")
    private String createUserId;

    @ApiModelProperty("创建用户昵称")
    private String createUsername;
}
