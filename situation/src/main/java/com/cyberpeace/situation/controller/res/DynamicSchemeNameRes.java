package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicSchemeNameRes implements Serializable {

	/**
	 * 方案id
	 */
	@ApiModelProperty("方案id")
	private String resourceId;

	/**
	 * 方案名称
	 */
	@ApiModelProperty("方案名称")
	private String name;

}
