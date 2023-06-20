package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel
public class TrainMangeCourseUpdateReq implements Serializable {

    @ApiModelProperty("培训框架Id")
    @NotBlank
    private String resourceId;

    @ApiModelProperty("课程ids")
    @NotBlank
    private String courseIds;

}
