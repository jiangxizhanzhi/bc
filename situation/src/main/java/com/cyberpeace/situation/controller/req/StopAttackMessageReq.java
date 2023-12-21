package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StopAttackMessageReq extends RunAttackMessageReq {

	@ApiModelProperty("模板id")
	private String id;


}
