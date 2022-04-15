package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowLogReq implements Serializable {

	@ApiModelProperty("场景id")
	@NotEmpty
	private String sceneId;

	@ApiModelProperty("起始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("时间间隔（秒）")
	private Integer interval;
}
