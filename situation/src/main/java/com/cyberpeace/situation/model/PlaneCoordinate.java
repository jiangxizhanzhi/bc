package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平面坐标
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PlaneCoordinate implements Serializable {

	/**
	 * x坐标
	 *
	 * @see Float
	 */
	@ApiModelProperty("x坐标")
	private Float x;

	/**
	 * y坐标
	 *
	 * @see Float
	 */
	@ApiModelProperty("y坐标")
	private Float y;

	public PlaneCoordinate(Float x, Float y) {
		this.x = x;
		this.y = y;
	}
}
