package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 评估算法响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalAlgorithmRsp {

	/**
	 * 评估算法id
	 */
	@ApiModelProperty(value = "算法Id", required = true)
	@NotNull
	private String evalAlgorithmId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("基本原理")
	private String description;

	@ApiModelProperty("使用说明")
	private String instructions;

	@ApiModelProperty("运用优势")
	private String advantage;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer type;

	/**
	 * 算法语言
	 */
	@ApiModelProperty(value = "算法语言 0:python,1:java", required = true)
	@NotNull
	private Integer algorithmLanguage;

	/**
	 * 代号
	 */
	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "矩阵代码", required = true)
	private String matrixData;

	/**
	 * 模式
	 */
	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer mode;

	/**
	 * 内置
	 */
	@ApiModelProperty(value = "是否为内置算法", required = true)
	@NotNull
	private Integer builtin;

	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数列表", required = true)
	@NotNull
	private List<AlgorithmParamContext> params = Lists.newArrayList();

	/**
	 * 参数数量
	 */
	@ApiModelProperty(value = "参数个数", required = true)
	@NotNull
	private Integer paramsNum;

}
