package com.cyberpeace.cr.crbase.controller.rsp.constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码名字返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CodeNameRsp {

	@ApiModelProperty("代号")
	Integer code;

	@ApiModelProperty("名称")
	String name;

	public CodeNameRsp(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
