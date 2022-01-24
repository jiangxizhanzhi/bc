package com.cyberpeace.cr.report_score.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebReportScoreListReq extends PageReqBase {
    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "科目")
    private Integer subject;

    @ApiModelProperty(value = "报告名称")
    private String reportName;
}
