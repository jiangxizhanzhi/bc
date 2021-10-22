package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CourseUpdateReq extends CourseAddReq {

    @ApiModelProperty("课程资源编号")
    private String resourceId;

}
