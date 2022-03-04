package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlaneCoordinate implements Serializable {

	@ApiModelProperty("x坐标")
	private Float x;

	@ApiModelProperty("y坐标")
	private Float y;

	public PlaneCoordinate(Float x, Float y) {
		this.x = x;
		this.y = y;
	}
}
