package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 变量
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class Variable extends VariableSimple implements Serializable {

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
