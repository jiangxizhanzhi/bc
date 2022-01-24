package com.cyberpeace.cr.report_score.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel
public class ReportScoreAddReq implements Serializable {

    @ApiModelProperty("报告名称")
    private String reportName;

    @ApiModelProperty("所属科目")
    private Integer subject;

    @ApiModelProperty("报告简介")
    private String reportDescription;

    @ApiModelProperty("报告附件")
    private String reportFile;

    @ApiModelProperty("报告状态")
    private Integer status;

}
