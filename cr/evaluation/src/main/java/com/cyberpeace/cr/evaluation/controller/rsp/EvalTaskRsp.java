package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskRsp extends SceneViewContext implements Serializable {

	@ApiModelProperty("测试名称")
	private Integer templateName;

	@ApiModelProperty("任务Id")
	private String taskId;

	@ApiModelProperty("任务名称")
	private String taskName;

}
