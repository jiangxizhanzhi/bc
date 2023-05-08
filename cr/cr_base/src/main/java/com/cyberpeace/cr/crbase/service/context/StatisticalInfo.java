package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class StatisticalInfo {

	@ApiModelProperty(value = "统计状态")
	private Integer statistical_state;

	@ApiModelProperty(value = "值")
	private Integer value;
}
