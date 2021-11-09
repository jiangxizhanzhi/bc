package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WriteUpReq {

	@ApiModelProperty(required = true)
	String trainingTeam;

	@ApiModelProperty(required = true)
	String missionResourceId;

	@ApiModelProperty(required = true)
	Boolean writeUpShow;

}
