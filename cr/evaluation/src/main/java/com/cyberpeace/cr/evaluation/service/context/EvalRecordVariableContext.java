package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordVariableContext implements Serializable {

    @ApiModelProperty(value = "变量项ID", required = true)
    private String resourceId;

    @ApiModelProperty("变量项名称")
    private String name;

    @ApiModelProperty(value = "场景主机节点ID", required = true)
    private String deviceId;

    @ApiModelProperty(value = "选择主机可用探针ID", required = true)
    private String probeId;

    @ApiModelProperty(value = "权重")
    private Integer weight;
}
