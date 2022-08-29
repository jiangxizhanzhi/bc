package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NfsUmountReq implements Serializable {

	@ApiModelProperty("挂载路径")
	@NotBlank
	private String path;

}
