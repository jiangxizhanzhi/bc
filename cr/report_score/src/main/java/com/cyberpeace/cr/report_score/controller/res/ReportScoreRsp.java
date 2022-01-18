package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("打分列表信息")
@Data
public class ReportScoreRsp implements Serializable {

    private Integer id;

    private String resourceId;

    @ApiModelProperty("报告名称")
    private String reportName;

    @ApiModelProperty("报告简介")
    private String reportDescription;

    @ApiModelProperty("所属科目")
    private String subject;

    @ApiModelProperty("战队名称")
    private String teamName;

    @ApiModelProperty("评分人")
    private String raterName;

    @ApiModelProperty("是否匿名")
    private Integer anonymous;

    @ApiModelProperty("提交时间")
    private Date submitTime;

    @ApiModelProperty("状态")
    private Integer status;


}
