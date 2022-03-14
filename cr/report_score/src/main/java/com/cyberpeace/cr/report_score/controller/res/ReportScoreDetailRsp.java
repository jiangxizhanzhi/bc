package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("打分详情信息")
@Data
public class ReportScoreDetailRsp extends ReportScoreRsp{
    @ApiModelProperty("报告附件")
    private String reportFile;

    @ApiModelProperty("报告附件名称")
    private String reportFileName;

    @ApiModelProperty("报告分数")
    private Float score;

    @ApiModelProperty("报告评语")
    private String comment;

    @ApiModelProperty("队伍名称")
    private String userName;

    @ApiModelProperty("队伍头像")
    private String avatarUrl;
}
