package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublicStatsBo implements Serializable {
    @ApiModelProperty("完成课时数量")
    private Integer lessonRecordCount = 0;

    @ApiModelProperty("完成实验数量")
    private Integer finishReportCount = 0;

    @ApiModelProperty("完成练习数量")
    private Integer finishPracticeCount = 0;

    @ApiModelProperty("完成考试数")
    private Integer finishExamCount = 0;

    @ApiModelProperty("实验平均得分")
    private float reportAverage = 0;

}
