package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ElementType implements Comparable<ElementType>{

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("资源名称")
	private String name;

	public ElementType(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}

	@Override
	public int compareTo(ElementType o) {
		return this.getName().compareTo(o.getName());
	}
}
