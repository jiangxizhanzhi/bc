package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskTimeNodesRsp {

	@ApiModelProperty("测评任务Id")
	private String taskId;

	@ApiModelProperty("测评步骤记录")
	private List<EvalTimeLineContext> data;

}
