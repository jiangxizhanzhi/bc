package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexContext {

	@ApiModelProperty(value = "指标Id", required = true)
	@NotNull
	private String indexId;

	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "描述", required = false)
	private String description;

	@ApiModelProperty(value = "算法id", required = true)
	@NotNull
	private String algorithmId;

	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	@ApiModelProperty(value = "体系id", required = true)
	@NotNull
	private String evalSystemId;

	@ApiModelProperty(value = "算法参数", required = true)
	@NotNull
	private String params;

	@ApiModelProperty(value = "子指标列表", required = true)
	@NotNull
	private List<String> childrenIds;

	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

	@ApiModelProperty(value = "指标计算错误信息")
	private String message;

	@ApiModelProperty(value = "得分")
	private Integer score;

	@ApiModelProperty(value = "进度")
	private Integer progress;

}