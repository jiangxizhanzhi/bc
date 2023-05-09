package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 变量列表数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class VariableListBo implements Serializable {

	/**
	 * 行为id
	 */
	@ApiModelProperty(value = "行为id")
	private String behaviorId;

	/**
	 * 变量列表
	 */
	@ApiModelProperty(value = "变量列表")
	private List<Variable> variables;
}
