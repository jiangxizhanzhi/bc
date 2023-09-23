package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalVariableProbeDataReq extends PageReqBase {
    @ApiModelProperty("采集变量项ID")
    private String variableId;

    public void setVariable_id(String variableId) {
        this.variableId = variableId;
    }
}
