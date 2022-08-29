package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NfsMountReq implements Serializable {

	@ApiModelProperty("主机")
	@NotBlank
	@ApiModelProperty(value = "主机地址", required = true)
	private String host;

	@ApiModelProperty("挂载路径")
	@NotBlank
	@ApiModelProperty(value = "挂载路径", required = true)
	private String path;

}
