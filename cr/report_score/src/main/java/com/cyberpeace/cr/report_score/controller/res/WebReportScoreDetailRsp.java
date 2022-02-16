package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("报告详情")
@Data
public class WebReportScoreDetailRsp extends WebReportScoreRsp{
    @ApiModelProperty("报告评语")
    private String comment;

    @ApiModelProperty("报告附件")
    private String reportFile;

    @ApiModelProperty("报告附件名称")
    private String reportFileName;

    @ApiModelProperty("得分")
    private Long score;

}
