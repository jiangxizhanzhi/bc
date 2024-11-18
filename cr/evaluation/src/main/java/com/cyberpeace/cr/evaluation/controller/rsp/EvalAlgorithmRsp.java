package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估算法响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalAlgorithmRsp {

	/**
	 * 评估算法id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "算法Id", required = true)
	@NotNull
	private String evalAlgorithmId;

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
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer type;

	/**
	 * 算法语言
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "算法语言", required = true)
	@NotNull
	private Integer algorithmLanguage;

	/**
	 * 代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	/**
	 * 矩阵数据
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "矩阵代码", required = true)
	private String matrixData;

	/**
	 * 模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer mode;

	/**
	 * 内置
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "是否为内置算法", required = true)
	@NotNull
	private Integer builtin;

	/**
	 * 参数
	 *
	 * @see List<AlgorithmParamContext>
	 */
	@ApiModelProperty(value = "参数列表", required = true)
	@NotNull
	private List<AlgorithmParamContext> params = Lists.newArrayList();

	/**
	 * 参数数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "参数个数", required = true)
	@NotNull
	private Integer paramsNum;

}
