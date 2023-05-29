package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRecordIndexContext implements Serializable {
    @ApiModelProperty(value = "指标项ID", required = true)
    private String resourceId;

    @ApiModelProperty(value = "三级指标项评分类型", required = true)
    private Integer type;

    @ApiModelProperty(value = "评分", required = false)
    private Integer score;

    @ApiModelProperty("指标项下关联变量项")
    private List<EvalRecordVariableContext> variables;

    @ApiModelProperty("指标项下关联聚合项")
    private List<EvalRecordIndexContext> indexes;

    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
