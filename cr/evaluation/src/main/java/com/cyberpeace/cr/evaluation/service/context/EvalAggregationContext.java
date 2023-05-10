package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalAggregationContext {

	@ApiModelProperty(value = "聚合项id", required = true)
	@NotNull
	private String evalAggregationId;

	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	@ApiModelProperty(value = "聚合算法id", required = true)
	@NotNull
	private String aggregatingAlgorithmId;

	@ApiModelProperty(value = "聚合算法参数", required = true)
	@NotNull
	private String aggregatingAlgorithmParams;

	@ApiModelProperty(value = "关联采集变量id(前端统一命名，需要转换一下存数据库)", required = true)
	@NotNull
	private List<String> aggregationIds;

	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}
