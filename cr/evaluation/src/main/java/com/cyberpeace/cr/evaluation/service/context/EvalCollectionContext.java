package com.cyberpeace.cr.evaluation.service.context;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * eval 集合上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalCollectionContext {

	/**
	 * 评估收集id
	 */
	@ApiModelProperty(value = "采集项id", required = true)
	@NotNull
	private String evalCollectionId;

	/**
	 * 评估指示器id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 格式算法id
	 */
	@ApiModelProperty(value = "格式化算法id", required = true)
	@NotNull
	private String formatAlgorithmId;

	/**
	 * 格式算法参数
	 */
	@ApiModelProperty(value = "格式化算法参数", required = true)
	@NotNull
	private String formatAlgorithmParams;

	/**
	 * 检波算法id
	 */
	@ApiModelProperty(value = "检测算法id", required = true)
	@NotNull
	private String detectionAlgorithmId;

	/**
	 * 检测算法参数
	 */
	@ApiModelProperty(value = "检测算法参数", required = true)
	@NotNull
	private String detectionAlgorithmParams;

	/**
	 * 得分算法id
	 */
	@ApiModelProperty(value = "评分算法id", required = true)
	@NotNull
	private String scoreAlgorithmId;

	/**
	 * 评分算法参数
	 */
	@ApiModelProperty(value = "评分算法参数", required = true)
	@NotNull
	private String scoreAlgorithmParams;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

}
