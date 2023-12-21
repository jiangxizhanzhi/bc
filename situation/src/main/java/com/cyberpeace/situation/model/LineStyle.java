package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LineStyle implements Serializable {

	@ApiModelProperty("颜色")
	private String color;

	@ApiModelProperty("线宽")
	private Integer width;

	@ApiModelProperty("线型")
	private String lineType;

	@ApiModelProperty("过程线路颜色（新增）")
	private String lineProcessColor;

	public LineStyle(String color, Integer width, String lineType, String lineProcessColor) {
		this.color = color;
		this.width = width;
		this.lineType = lineType;
		this.lineProcessColor = lineProcessColor;
	}

	public LineStyle() {}

}
