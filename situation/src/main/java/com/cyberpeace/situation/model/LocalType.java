package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LocalType {

	/**
	 * 类型编码
	 */
	@ApiModelProperty("类型编码")
	private String code;

	/**
	 * 类型名称
	 */
	@ApiModelProperty("类型名称")
	private String msg;

	public LocalType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
