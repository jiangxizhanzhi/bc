package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务id响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskIdRsp implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String taskId;

	public EvalTaskIdRsp(String taskId) {
		this.taskId = taskId;
	}

}
