package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("资源授权访问")
public class ResourceAuthReq {

	@ApiModelProperty("资源ID")
	@NotEmpty
	private List<String> resourceIds;

	@ApiModelProperty("授权资源")
	@NotEmpty
	private List<String> readAuthIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer readAuthType;

}
