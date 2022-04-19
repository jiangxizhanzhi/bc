package com.cyberpeace.cr.hld.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class HldDeleteReq implements Serializable {

	@ApiModelProperty
	private List<String> ids;

	@ApiModelProperty(value = "HLD用户id", required = false)
	private String userId = "task";
}
