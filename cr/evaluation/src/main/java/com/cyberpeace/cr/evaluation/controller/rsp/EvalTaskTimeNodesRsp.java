package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务时间节点响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalTaskTimeNodesRsp implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 数据
	 */
	@ApiModelProperty("测评步骤记录")
	private List<EvalTimeLineContext> data;

}
