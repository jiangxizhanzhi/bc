package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import lombok.Data;

@Data
public class EvalTaskSlimContext {

	private String taskId;

	private String taskName;

	private String mpSceneId;

	public void setResourceId(String resourceId) {
		this.taskId = resourceId;
	}

	public String getTaskName() {
		if (this.taskName == null) {
			return BusinessException.getMessageByLanguage(CrBusinessErrorCode.CURRENT_TEST_INSTANCE);
		} else {
			return taskName;
		}
	}
}
