package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StringType {

	@ApiModelProperty("类型编码")
	private String code;

	@ApiModelProperty("类型名称")
	private String name;

	public StringType(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
