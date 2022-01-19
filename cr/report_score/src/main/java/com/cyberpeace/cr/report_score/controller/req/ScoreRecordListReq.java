package com.cyberpeace.cr.report_score.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ScoreRecordListReq extends PageReqBase {
    @ApiModelProperty(value = "科目")
    @NotNull
    private Integer subject;
}
