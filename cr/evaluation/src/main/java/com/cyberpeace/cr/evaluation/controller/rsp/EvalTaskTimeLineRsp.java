package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskTimeLineRsp implements Serializable {

	@ApiModelProperty("测试靶场模板Id")
	private Integer templateId;

	@ApiModelProperty("任务Id")
	private String taskId;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("预计结束时间 默认为最近一次活动时间+1天")
	private Date endTime;

	@ApiModelProperty("测试步骤记录")
	private List<EvalTaskStepContext> data;

	@ApiModelProperty("当前时间")
	private Date currentTime;

}
