package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EvalMissionLogRsp {


	@ApiModelProperty(value = "任务名称")
	@NotNull
	private String name;

	@ApiModelProperty(value = "日志id")
	private String resourceId;

	@ApiModelProperty(value = "开始时间")
	@NotNull
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

}
