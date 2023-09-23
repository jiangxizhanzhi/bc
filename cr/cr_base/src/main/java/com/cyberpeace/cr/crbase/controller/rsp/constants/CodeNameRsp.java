package com.cyberpeace.cr.crbase.controller.rsp.constants;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
