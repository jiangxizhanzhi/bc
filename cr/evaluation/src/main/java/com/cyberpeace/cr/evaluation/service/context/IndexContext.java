package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 索引上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class IndexContext {

	/**
	 * 指数id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "指标Id", required = true)
	@NotNull
	private String indexId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 算法id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "算法id", required = true)
	@NotNull
	private String algorithmId;

	/**
	 * 评估指示器id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 评估系统id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "体系id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 参数
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "算法参数", required = true)
	@NotNull
	private String params;

	/**
	 * 子级id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "子指标列表", required = true)
	@NotNull
	private List<String> childrenIds;

	/**
	 * 矩阵数据
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}