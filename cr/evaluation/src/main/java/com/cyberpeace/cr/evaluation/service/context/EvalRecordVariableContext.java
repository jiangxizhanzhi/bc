package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EvalRecordVariableContext implements Serializable {

    @ApiModelProperty("变量项ID")
    @NotNull
    private String variableId;

    @ApiModelProperty("变量项名称")
    private String name;

    @ApiModelProperty("场景主机节点ID")
    @NotNull
    private String deviceId;

    @ApiModelProperty("选择主机可用探针ID")
    @NotNull
    private String probeId;
}
