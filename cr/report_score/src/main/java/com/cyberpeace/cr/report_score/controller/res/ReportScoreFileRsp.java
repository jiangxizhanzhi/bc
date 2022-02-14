package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("报告附件")
@Data
public class ReportScoreFileRsp implements Serializable {
    @ApiModelProperty("报告名称")
    private String fileName;

    @ApiModelProperty("报告链接")
    private String fileUrl;
}
