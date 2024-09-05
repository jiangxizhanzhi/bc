package com.cyberpeace.cr.crbase.controller.rsp.constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StrCodeNameRsp implements Serializable {

	@ApiModelProperty("代号")
	String code;

	@ApiModelProperty("名称")
	String name;

	public StrCodeNameRsp(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
