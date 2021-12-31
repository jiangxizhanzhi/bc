package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonalStatsDetail4CountBo implements Serializable {
    @ApiModelProperty("课程总数")
    private Integer courseTotalCount = 0;

    @ApiModelProperty("完成理论课程数量")
    private Integer theoryCourseTotalCount = 0;

    @ApiModelProperty("实验课程数量")
    private Integer expCourseTotalCount = 0;

    @ApiModelProperty("课后练习数量")
    private Integer afterClassPracticeTotalCount = 0;

    @ApiModelProperty("完成课程数量")
    private Integer finishCoursesCount = 0;

    @ApiModelProperty("完成理论课程数量")
    private Integer finishTheoryCount = 0;

    @ApiModelProperty("完成实验课程数量")
    private Integer finishExpCount = 0;

    @ApiModelProperty("完成课后练习数量")
    private Integer afterClassPractice = 0;

}
