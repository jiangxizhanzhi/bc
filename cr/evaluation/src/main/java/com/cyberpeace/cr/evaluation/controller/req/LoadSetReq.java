package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoadSetReq implements Serializable {

	@ApiModelProperty(value = "载荷id", required = true)
	private String loadId;

	@ApiModelProperty(value = "界面类型", required = true)
	private Integer interfaceType;

	@ApiModelProperty(value = "账号", required = true)
	private String account;

	@ApiModelProperty(value = "密码", required = true)
	private String password;

	@ApiModelProperty(value = "策略", required = true)
	private String strategy;
}
