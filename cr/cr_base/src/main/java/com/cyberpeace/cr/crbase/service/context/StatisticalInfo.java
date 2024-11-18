package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class StatisticalInfo {

	/**
	 * 统计状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "统计状态")
	private Integer statistical_state;

	/**
	 * 值
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "值")
	private Integer value;
}
