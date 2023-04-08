package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BarChartUnit implements Serializable {

	@ApiModelProperty("横坐标")
	private String name;

	@ApiModelProperty("纵坐标")
	private Integer score;

	public BarChartUnit(String attackTypeName, Integer score) {
		this.name = attackTypeName;
		this.score = score;
	}
}
