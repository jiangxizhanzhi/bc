package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计信息
 */
@Data
public class StatisticalInfo {

	@ApiModelProperty(value = "统计状态")
	private Integer statisticalState;

	@ApiModelProperty(value = "值")
	private Integer value;
}
