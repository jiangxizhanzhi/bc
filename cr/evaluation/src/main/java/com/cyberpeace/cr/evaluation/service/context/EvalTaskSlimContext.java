package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EvalTaskSlimContext {

	private String taskId;

	private String taskName = "当前测试实例";

	private String mpSceneId;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private LocalDateTime startSearchTime;

	private LocalDateTime endSearchTime;

	public void setResourceId(String resourceId) {
		this.taskId = resourceId;
	}

}
