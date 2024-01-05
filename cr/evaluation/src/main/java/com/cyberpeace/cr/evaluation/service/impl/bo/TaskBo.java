package com.cyberpeace.cr.evaluation.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaskBo implements Serializable {

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务状态，默认未开始")
	private Integer status;
}
