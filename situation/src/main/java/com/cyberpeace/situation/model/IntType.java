package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IntType {

	@ApiModelProperty("类型编码")
	private Integer code;

	@ApiModelProperty("类型名称")
	private String name;

	public IntType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
