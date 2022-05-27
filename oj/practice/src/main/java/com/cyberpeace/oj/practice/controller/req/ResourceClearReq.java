package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ResourceClearReq implements Serializable {

	@ApiModelProperty("资源编号")
	@NotEmpty
	private String resourceId;
}
