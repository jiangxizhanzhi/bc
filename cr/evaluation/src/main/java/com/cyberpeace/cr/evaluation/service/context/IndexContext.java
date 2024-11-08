package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 索引上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class IndexContext {

	/**
	 * 指数id
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
	 * 评估指示器id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 评估系统id
	 */
	@ApiModelProperty(value = "体系id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 参数
	 */
	@ApiModelProperty(value = "算法参数", required = true)
	@NotNull
	private String params;

	/**
	 * 子级id列表
	 */
	@ApiModelProperty(value = "子指标列表", required = true)
	@NotNull
	private List<String> childrenIds;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}