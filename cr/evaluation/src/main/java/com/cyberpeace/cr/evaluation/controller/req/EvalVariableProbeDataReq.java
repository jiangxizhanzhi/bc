package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;

@Data
public class EvalVariableProbeDataReq extends PageReqBase {
    private String variableId;

    public void setVariable_id(String variableId) {
        this.variableId = variableId;
    }
}
