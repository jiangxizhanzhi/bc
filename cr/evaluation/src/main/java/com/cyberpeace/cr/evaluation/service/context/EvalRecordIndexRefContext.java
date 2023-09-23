package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordIndexRefContext implements Serializable {
    @ApiModelProperty(value = "指标项ID", required = true)
    private String resourceId;

    @ApiModelProperty(value = "三级指标项评分类型", required = true)
    private Integer type;

    @ApiModelProperty(value = "评分", required = false)
    private Integer score;

    @ApiModelProperty("指标项下关联变量项")
    private List<EvalRecordIndexContext> variables;

    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
