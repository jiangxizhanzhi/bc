package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务时间节点响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTaskTimeNodesRsp {

	/**
	 * 测评任务Id
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 测评步骤记录
	 */
	@ApiModelProperty("测评步骤记录")
	private List<EvalTimeLineContext> data;

}
