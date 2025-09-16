package com.cyberpeace.oj.course.controller.res;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseTrainTaskRes {


    @ApiModelProperty("下发的任务数量")
    private String publishTaskNums;

    @ApiModelProperty("完成的任务数量")
    private String successTaskNums;
}
