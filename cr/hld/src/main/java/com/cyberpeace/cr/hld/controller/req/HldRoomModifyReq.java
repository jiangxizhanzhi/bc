package com.cyberpeace.cr.hld.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HldRoomModifyReq extends HldRoomAddReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

}