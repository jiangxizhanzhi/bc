package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.integratation.model.situation.req.EventReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventMsgReq extends EventReq implements Serializable {

	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	@ApiModelProperty("特征ID")
	@NotEmpty
	private String featureId;

}
