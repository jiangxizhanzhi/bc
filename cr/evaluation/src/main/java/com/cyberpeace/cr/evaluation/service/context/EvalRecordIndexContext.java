package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评记录编号上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordIndexContext implements Serializable {
	/**
	 * 指标项ID
	 */
    @ApiModelProperty(value = "指标项ID", required = true)
    private String resourceId;

	/**
	 * 三级指标项评分类型
	 */
    @ApiModelProperty(value = "三级指标项评分类型", required = true)
    private Integer type;

	/**
	 * 评分
	 */
    @ApiModelProperty(value = "评分", required = false)
    private Integer score;

	/**
	 * 指标项下关联变量项
	 */
    @ApiModelProperty("指标项下关联变量项")
    private List<EvalRecordVariableContext> variables;

	/**
	 * 指标项下关联聚合项
	 */
    @ApiModelProperty("指标项下关联聚合项")
    private List<EvalRecordIndexContext> indexes;

	/**
	 * 指标项专家评分配置
	 */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
