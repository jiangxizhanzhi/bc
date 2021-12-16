package com.cyberpeace.cr.redvsblue.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbRoomModifyReq extends RbRoomReq {

	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;
}