package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WriteUpReq {

	@ApiModelProperty(required = true)
	private String trainingTeam;

	@ApiModelProperty(required = true)
	private String missionResourceId;

	@ApiModelProperty(required = true)
	private Boolean writeUpShow;

}
