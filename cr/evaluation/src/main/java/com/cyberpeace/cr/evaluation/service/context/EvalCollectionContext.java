package com.cyberpeace.cr.evaluation.service.context;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalCollectionContext {

	@ApiModelProperty(value = "采集项id", required = true)
	@NotNull
	private String evalCollectionId;

	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	@ApiModelProperty(value = "格式化算法id", required = true)
	@NotNull
	private String formatAlgorithmId;

	@ApiModelProperty(value = "格式化算法参数", required = true)
	@NotNull
	private String formatAlgorithmParams;

	@ApiModelProperty(value = "检测算法id", required = true)
	@NotNull
	private String detectionAlgorithmId;

	@ApiModelProperty(value = "检测算法参数", required = true)
	@NotNull
	private String detectionAlgorithmParams;

	@ApiModelProperty(value = "评分算法id", required = true)
	@NotNull
	private String scoreAlgorithmId;

	@ApiModelProperty(value = "评分算法参数", required = true)
	@NotNull
	private String scoreAlgorithmParams;

	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "得分", required = true)
	private Integer score;

	@ApiModelProperty(value = "错误信息", required = true)
	private String message;

}
