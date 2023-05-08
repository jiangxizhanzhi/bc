package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为变量数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class VariableBo implements Serializable {

	/**
	 * 变量名称
	 */
	@ApiModelProperty(value = "变量名")
	private String name;

	/**
	 * 变量值
	 */
	@ApiModelProperty(value = "变量值")
	private String value;
}
