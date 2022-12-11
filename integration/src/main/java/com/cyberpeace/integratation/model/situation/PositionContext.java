package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PositionContext implements Serializable {

	@ApiModelProperty("x坐标")
	private Float x;

	@ApiModelProperty("y坐标")
	private Float y;

	public PositionContext(Float x, Float y) {
		this.x = x;
		this.y = y;
	}
}
