package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResourcePublicReq implements Serializable {

	@ApiModelProperty("更新资源ID")
	@NotEmpty
	private List<String> resourceIds;

	@ApiModelProperty("是否公开")
	@NotNull
	private Boolean isPublic;
}
