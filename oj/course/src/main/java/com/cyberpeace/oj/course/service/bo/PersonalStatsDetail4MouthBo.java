package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PersonalStatsDetail4MouthBo implements Serializable {

    @ApiModelProperty("近30天日期")
    private List<String> thirtyDays;

    @ApiModelProperty("学习课程(实际数据为课时)")
    private List<Integer> lessonLearnRecord;

    @ApiModelProperty("完成报告")
    private List<Integer> reportFinishRecord;

    @ApiModelProperty("报告平均分")
    private List<Integer> averageScore;

}
