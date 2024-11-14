package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ElementType {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 资源名称
	 */
	@ApiModelProperty("资源名称")
	private String name;

	public ElementType(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
