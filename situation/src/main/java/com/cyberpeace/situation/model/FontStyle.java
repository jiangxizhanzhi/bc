package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字形
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FontStyle implements Serializable {

	/**
	 * 颜色
	 *
	 * @see String
	 */
	@ApiModelProperty("颜色")
	private String color;

	/**
	 * 大小（px）
	 *
	 * @see Integer
	 */
	@ApiModelProperty("大小（px）")
	private Integer size;

	public FontStyle(String color, Integer size) {
		this.color = color;
		this.size = size;
	}

	public FontStyle() {}

}
