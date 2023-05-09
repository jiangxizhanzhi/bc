package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 变量修改请求体
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class VariableModifyReq implements Serializable {

	/**
	 * 行为id
	 */
	@ApiModelProperty(value = "行为id")
	private String behaviorId;

	/**
	 * 行为变量
	 */
	@ApiModelProperty(value = "行为变量")
	private List<VariableSimple> variables;
}
