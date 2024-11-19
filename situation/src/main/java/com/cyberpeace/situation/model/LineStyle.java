package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 线条样式
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class LineStyle implements Serializable {

	/**
	 * 颜色
	 *
	 * @see String
	 */
	@ApiModelProperty("颜色")
	private String color;

	/**
	 * 线宽
	 *
	 * @see Integer
	 */
	@ApiModelProperty("线宽")
	private Integer width;

	/**
	 * 线型
	 *
	 * @see String
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
