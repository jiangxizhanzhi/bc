package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评指示器上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalIndicatorContext {

	/**
	 * 指标项id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 聚合算法id
	 */
	@ApiModelProperty(value = "聚合算法id", required = true)
	@NotNull
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	@ApiModelProperty(value = "聚合算法参数", required = true)
	@NotNull
	private String aggregatingAlgorithmParams;

	/**
	 * 关联聚合项id
	 */
	@ApiModelProperty(value = "关联聚合项id", required = true)
	@NotNull
	private List<String> aggregationIds;

	/**
	 * 关联采集项id
	 */
	@ApiModelProperty(value = "关联采集项id", required = false)
	@NotNull
	private List<String> collectionIds;

	/**
	 * 层次分析法json数组
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}
