package com.cyberpeace.cr.report_score.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ReportScoreListReq extends PageReqBase {
    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "科目")
    private Integer subject;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "战队/报告名称")
    private String name;

}
