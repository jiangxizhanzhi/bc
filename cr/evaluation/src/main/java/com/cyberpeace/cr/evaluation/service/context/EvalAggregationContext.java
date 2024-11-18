package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * eval 聚合上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalAggregationContext {

	/**
	 * 评估集合体id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "聚合项id", required = true)
	@NotNull
	private String evalAggregationId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 评估指示器id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 聚合算法id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "聚合算法id", required = true)
	@NotNull
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "聚合算法参数", required = true)
	@NotNull
	private String aggregatingAlgorithmParams;

	/**
	 * 集合体id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "关联采集变量id(前端统一命名，需要转换一下存数据库)", required = true)
	@NotNull
	private List<String> aggregationIds;

	/**
	 * 矩阵数据
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}
