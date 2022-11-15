package com.cyberpeace.bs.service.bo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 饼状图键值对
 */
@Data
public class NameValue {

	@ApiModelProperty(value = "标签")
	private String name;

	@ApiModelProperty(value = "值")
	private Integer value;

	public NameValue(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
}