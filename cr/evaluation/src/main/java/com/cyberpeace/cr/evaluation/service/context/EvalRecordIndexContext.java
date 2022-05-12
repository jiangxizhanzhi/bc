package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRecordIndexContext implements Serializable {
    @ApiModelProperty("指标项ID")
    private String resourceId;

    @ApiModelProperty("指标项默认类型")
    private Integer type;

    @ApiModelProperty("指标项下关联变量项")
    private List<EvalRecordVariableContext> variables;

    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
