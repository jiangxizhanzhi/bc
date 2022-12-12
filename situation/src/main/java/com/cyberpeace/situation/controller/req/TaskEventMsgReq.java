package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.integratation.model.situation.req.TaskEventReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskEventMsgReq extends TaskEventReq implements Serializable {

	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	@ApiModelProperty("特征ID")
	@NotEmpty
	private String featureId;

}
