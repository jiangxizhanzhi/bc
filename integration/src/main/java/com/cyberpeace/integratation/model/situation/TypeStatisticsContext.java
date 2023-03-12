package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TypeStatisticsContext implements Serializable {

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("数量")
	private Integer count;

	public TypeStatisticsContext() {}

	public TypeStatisticsContext(String type, Integer value) {
		this.type = type;
		this.count = value;
	}

}
