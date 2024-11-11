package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 个人数据详情4口无双
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PersonalStatsDetail4MouthBo implements Serializable {

    /**
     * 三十天
     */
    @ApiModelProperty("近30天日期")
    private List<String> thirtyDays;

    /**
     * 课程学习记录
     */
    @ApiModelProperty("学习课程(实际数据为课时)")
    private List<Integer> lessonLearnRecord;

    /**
     * 报告完成记录
     */
    @ApiModelProperty("完成报告")
    private List<Integer> reportFinishRecord;

    /**
     * 平均分
     */
    @ApiModelProperty("报告平均分")
    private List<Integer> averageScore;

}
