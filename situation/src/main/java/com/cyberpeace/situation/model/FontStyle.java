package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FontStyle implements Serializable {

	@ApiModelProperty("颜色")
	private String color;

	@ApiModelProperty("大小（px）")
	private Integer size;

	public FontStyle(String color, Integer size) {
		this.color = color;
		this.size = size;
	}

	public FontStyle() {}

}
