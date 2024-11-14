package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexContext {

	/**
	 * 指标Id
	 */
	@ApiModelProperty(value = "指标Id", required = true)
	@NotNull
	private String indexId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 算法id
	 */
	@ApiModelProperty(value = "算法id", required = true)
	@NotNull
	private String algorithmId;

	/**
	 * 指标项id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 体系id
	 */
	@ApiModelProperty(value = "体系id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 算法参数
	 */
	@ApiModelProperty(value = "算法参数", required = true)
	@NotNull
	private String params;

	/**
	 * 子指标列表
	 */
	@ApiModelProperty(value = "子指标列表", required = true)
	@NotNull
	private List<String> childrenIds;

	/**
	 * 层次分析法json数组
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}