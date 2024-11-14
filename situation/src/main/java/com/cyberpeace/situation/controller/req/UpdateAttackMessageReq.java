package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateAttackMessageReq extends AttackMessageReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

}
