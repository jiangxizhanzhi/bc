package com.cyberpeace.cr.report_score.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class ReportScoreUpdateReq extends ReportScoreAddReq{
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;
}
