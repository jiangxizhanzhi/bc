package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModelProperty;

public class WebReportScoreDetailRsp extends WebReportScoreRsp{
    @ApiModelProperty("报告评语")
    private String comment;

    @ApiModelProperty("报告附件")
    private String reportFile;

}
