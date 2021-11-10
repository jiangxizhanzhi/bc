package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel
@Data
public class ResourceDelReq implements Serializable {

	@ApiModelProperty("资源编号")
	@NotEmpty
	private List<String> resourceIds;
}
