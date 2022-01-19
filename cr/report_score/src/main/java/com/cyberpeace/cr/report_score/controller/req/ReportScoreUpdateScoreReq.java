package com.cyberpeace.cr.report_score.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class ReportScoreUpdateScoreReq implements Serializable {
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;

    @ApiModelProperty("是否匿名")
    private Integer anonymous;

    @ApiModelProperty("报告状态")
    private Integer status;

    @ApiModelProperty("报告评语")
    private String comment;

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("评分人id")
    private String raterId;

    @ApiModelProperty("评分人姓名")
    private String raterName;

}
