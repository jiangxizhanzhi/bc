package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class TrainCourseReq implements Serializable {


    @ApiModelProperty("培训框架编号")
    @NotBlank
    private String resourceId;

    @ApiModelProperty("课程编号")
    @NotNull
    private List<String> courseIdList;

}
