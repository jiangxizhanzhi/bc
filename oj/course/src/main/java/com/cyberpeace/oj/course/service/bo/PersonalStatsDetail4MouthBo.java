package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 个人数据详情4口无双
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PersonalStatsDetail4MouthBo implements Serializable {

    /**
     * 三十天
     *
     * @see List<String>
     */
    @ApiModelProperty("近30天日期")
    private List<String> thirtyDays;

    /**
     * 课程学习记录
     *
     * @see List<Integer>
     */
    @ApiModelProperty("学习课程(实际数据为课时)")
    private List<Integer> lessonLearnRecord;

    /**
     * 报告完成记录
     *
     * @see List<Integer>
     */
    @ApiModelProperty("完成报告")
    private List<Integer> reportFinishRecord;

    /**
     * 平均分
     *
     * @see List<Integer>
     */
    @ApiModelProperty("报告平均分")
    private List<Integer> averageScore;

}
