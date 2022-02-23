package com.cyberpeace.cr.hld.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HldRoomModifyReq extends HldRoomAddReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

}