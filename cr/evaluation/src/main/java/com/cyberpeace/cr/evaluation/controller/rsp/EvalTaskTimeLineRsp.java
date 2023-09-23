package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskTimeLineRsp {

	@ApiModelProperty("测评任务Id")
	private String taskId;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("结束时间")
	private Date endTime;

	@ApiModelProperty("测评步骤记录")
	private List<EvalTimeLineContext> data;

	@ApiModelProperty("当前时间")
	private Date currentTime;

}
