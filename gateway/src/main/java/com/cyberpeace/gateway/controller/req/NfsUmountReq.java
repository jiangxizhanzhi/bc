package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NfsUmountReq implements Serializable {

	@NotBlank
	@ApiModelProperty(value = "卸载路径", required = true)
	private String path;

}
