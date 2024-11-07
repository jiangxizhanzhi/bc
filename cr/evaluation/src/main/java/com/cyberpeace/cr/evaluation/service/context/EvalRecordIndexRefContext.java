package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * eval record index ref 上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalRecordIndexRefContext implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty(value = "指标项ID", required = true)
    private String resourceId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "三级指标项评分类型", required = true)
    private Integer type;

    /**
     * 得分
     */
    @ApiModelProperty(value = "评分", required = false)
    private Integer score;

    /**
     * 变量
     */
    @ApiModelProperty("指标项下关联变量项")
    private List<EvalRecordIndexContext> variables;

    /**
     * 专家
     */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
