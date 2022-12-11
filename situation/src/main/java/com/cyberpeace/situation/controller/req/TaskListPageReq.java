package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskListPageReq extends PageReqBase implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("任务类型")
	private String taskType;

	@ApiModelProperty("任务模式")
	private String taskMode;

	@ApiModelProperty("任务状态")
	private String taskStatus;

}
