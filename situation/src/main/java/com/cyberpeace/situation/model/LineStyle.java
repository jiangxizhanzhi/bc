package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LineStyle implements Serializable {

	/**
	 * 颜色
	 */
	@ApiModelProperty("颜色")
	private String color;

	/**
	 * 线宽
	 */
	@ApiModelProperty("线宽")
	private Integer width;

	/**
	 * 线型
	 */
	@ApiModelProperty("线型")
	private String lineType;

	public LineStyle(String color, Integer width, String lineType) {
		this.color = color;
		this.width = width;
		this.lineType = lineType;
	}

	public LineStyle() {}

}
