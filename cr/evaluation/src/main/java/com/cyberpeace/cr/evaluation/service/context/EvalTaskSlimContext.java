package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

@Data
public class EvalTaskSlimContext {

	private String taskId;

	private String taskName = "当前测试实例";

	private String mpSceneId;

	private String startTime;

	private String endTime;

	public void setResourceId(String resourceId) {
		this.taskId = resourceId;
	}

}
