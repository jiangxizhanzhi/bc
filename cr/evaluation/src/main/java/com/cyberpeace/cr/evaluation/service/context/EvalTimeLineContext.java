package com.cyberpeace.cr.evaluation.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTimeLineContext {

	@ApiModelProperty("步骤Id")
	private String stepId;

	@ApiModelProperty("步骤标题")
	private String title;

	@ApiModelProperty("完成时间")
	private Date endTime;

	public void setResourceId(String resourceId) {
		this.stepId = resourceId;
	}

}
