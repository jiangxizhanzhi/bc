package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardBasicReq implements Serializable {

	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	@ApiModelProperty("特征ID")
	@NotEmpty
	private String featureId;
}
