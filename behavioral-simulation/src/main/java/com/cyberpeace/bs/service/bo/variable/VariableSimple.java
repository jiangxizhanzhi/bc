package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 变量简要数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class VariableSimple implements Serializable {

	/**
	 * 变量id
	 */
	@ApiModelProperty(value = "变量id")
	private String id;

	/**
	 * 变量名称
	 */
	@ApiModelProperty(value = "变量名称")
	private String name;

	/**
	 * 变量值
	 */
	@ApiModelProperty(value = "变量值")
	private String value;

	/**
	 * 变量备注
	 */
	@ApiModelProperty(value = "变量备注")
	private String note;
}
