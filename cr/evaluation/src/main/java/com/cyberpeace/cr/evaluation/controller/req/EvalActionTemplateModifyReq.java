package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalActionTemplateModifyReq extends EvalActionTemplateAddReq {

	@ApiModelProperty("动作id")
	private String resourceId;

}
