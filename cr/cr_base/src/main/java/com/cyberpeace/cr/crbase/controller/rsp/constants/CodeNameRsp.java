package com.cyberpeace.cr.crbase.controller.rsp.constants;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代号名称响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class CodeNameRsp implements Serializable {

	/**
	 * 代号
	 */
	@ApiModelProperty("代号")
	Integer code;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	String name;

	public CodeNameRsp(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
