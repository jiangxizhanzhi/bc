package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 公开统计 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PublicStatsBo implements Serializable {
    /**
     * 课记录计数
     */
    @ApiModelProperty("完成课时数量")
    private Integer lessonRecordCount = 0;

    /**
     * 完成实践计数
     */
    @ApiModelProperty("完成练习数量")
    private Integer finishPracticeCount = 0;

    /**
     * 完成报告计数
     */
    @ApiModelProperty("完成实验数量")
    private Integer finishReportCount = 0;

    /**
     * 报告平均值
     */
    @ApiModelProperty("实验平均得分")
    private float reportAverage = 0;

    /**
     * 完成考试计数
     */
    @ApiModelProperty("完成考试数")
    private Integer finishExamCount = 0;
}
