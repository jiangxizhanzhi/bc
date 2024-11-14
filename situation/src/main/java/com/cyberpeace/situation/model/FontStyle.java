package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FontStyle implements Serializable {

	/**
	 * 颜色
	 */
	@ApiModelProperty("颜色")
	private String color;

	/**
	 * 大小（px）
	 */
	@ApiModelProperty("大小（px）")
	private Integer size;

	public FontStyle(String color, Integer size) {
		this.color = color;
		this.size = size;
	}

	public FontStyle() {}

}
