package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class StatisticalInfo {

	/**
	 * 统计状态
	 */
	@ApiModelProperty(value = "统计状态")
	private Integer statistical_state;

	/**
	 * 值
	 */
	@ApiModelProperty(value = "值")
	private Integer value;
}
