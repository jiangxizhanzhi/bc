package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红点
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/06/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class BadgeBo implements Serializable {

	/**
	 * 显示
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否显示")
	private Boolean show;

	// @ApiModelProperty(value = "")
	// private Integer number;
}
