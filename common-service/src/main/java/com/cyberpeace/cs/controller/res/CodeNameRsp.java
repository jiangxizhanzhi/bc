package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CodeNameRsp implements Serializable {

	@ApiModelProperty("代号")
	Integer code;

	@ApiModelProperty("名称")
	String name;

	public CodeNameRsp(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
