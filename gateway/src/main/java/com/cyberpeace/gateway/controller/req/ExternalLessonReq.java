package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.cs.service.context.IdListReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExternalLessonReq extends IdListReq {

	@ApiModelProperty(value = "是否只看公开")
	private Boolean isPublic;
}
