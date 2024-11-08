package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务时间线响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalTaskTimeLineRsp implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private Date endTime;

	/**
	 * 数据
	 */
	@ApiModelProperty("测评步骤记录")
	private List<EvalTimeLineContext> data;

	/**
	 * 当前时间
	 */
	@ApiModelProperty("当前时间")
	private Date currentTime;

}
