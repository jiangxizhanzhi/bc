package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 红点
 *
 * @author SN
 * @date 2023/06/08
 */
@Getter
@Setter
public class BadgeBo implements Serializable {

	/**
	 * 显示
	 */
	@ApiModelProperty(value = "是否显示")
	private Boolean show;

	// @ApiModelProperty(value = "")
	// private Integer number;
}
