package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红点
 *
 * @author SN
 */
@Data
public class BadgeBo implements Serializable {

	@ApiModelProperty(value = "是否显示")
	private Boolean show;

	// @ApiModelProperty(value = "")
	// private Integer number;
}
