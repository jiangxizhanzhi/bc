package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value = "变量项评分类型", required = false)
    private Integer type;

    @ApiModelProperty(value = "评分", required = false)
    private Integer score;

    @ApiModelProperty(value = "关联的五级指标信息", required = false)
    private EvalRecordLoadFifthIndexContext evalRecordLoadFifthIndexContext;
}
