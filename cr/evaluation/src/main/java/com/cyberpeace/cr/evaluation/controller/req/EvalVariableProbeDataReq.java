package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估variable探针数据请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalVariableProbeDataReq extends PageReqBase {
    /**
     * 采集变量项ID
     */
    @ApiModelProperty("采集变量项ID")
    private String variableId;

    public void setVariable_id(String variableId) {
        this.variableId = variableId;
    }
}
