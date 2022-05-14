package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

@Data
public class EvalTaskSlimContext {

	private String taskId;

	private String taskName;

	private String mpSceneId;

	public void setResourceId(String resourceId) {
		this.taskId = resourceId;
	}

}
