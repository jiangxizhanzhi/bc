package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NfsMountReq implements Serializable {

	@ApiModelProperty("主机")
	@NotBlank
	private String host;

	@ApiModelProperty("挂载路径")
	@NotBlank
	private String path;

}
