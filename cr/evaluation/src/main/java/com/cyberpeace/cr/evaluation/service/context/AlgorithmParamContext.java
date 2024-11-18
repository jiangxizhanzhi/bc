package com.cyberpeace.cr.evaluation.service.context;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * algorithm param 上下文
 *
 * @author SN
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/07
 */
@Data
public class AlgorithmParamContext {

	/**
	 * 参数名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "参数名称", required = true)
	@NotNull
	private String paramName;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String desc;

	/**
	 * param 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "参数类型", required = true)
	@NotNull
	private String paramType;

	/**
	 * 默认值
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "默认值", required = true)
	@NotNull
	private String defaultValue;
}
