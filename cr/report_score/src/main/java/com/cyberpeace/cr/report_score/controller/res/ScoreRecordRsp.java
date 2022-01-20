package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("科目评分记录")
@Data
public class ScoreRecordRsp implements Serializable {
    @ApiModelProperty("报告名称")
    private String reportName;

    @ApiModelProperty("队伍名称")
    private String teamName;

    @ApiModelProperty("评分时间")
    private Date scoreTime;

    @ApiModelProperty("得分")
    private Integer score;
}
