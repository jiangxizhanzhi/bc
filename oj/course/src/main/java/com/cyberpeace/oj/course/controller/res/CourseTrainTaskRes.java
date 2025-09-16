package com.cyberpeace.oj.course.controller.res;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTrainTaskRes {


    @ApiModelProperty("下发的任务数量")
    private Integer publishTaskNums;

    @ApiModelProperty("完成的任务数量")
    private Integer successTaskNums;
}
