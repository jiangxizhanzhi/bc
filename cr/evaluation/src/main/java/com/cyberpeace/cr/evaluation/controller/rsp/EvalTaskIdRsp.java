package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务id响应
 *
 * @author SN
 * @version 1.0.0
 * @see Serializable
 * @since 1.0.0
 * @date 2024/11/07
 */
@Data
public class EvalTaskIdRsp implements Serializable {

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String taskId;

	public EvalTaskIdRsp(String taskId) {
		this.taskId = taskId;
	}

}
