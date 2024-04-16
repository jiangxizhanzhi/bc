package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import lombok.Data;

@Data
public class EvalTaskSlimContext {

	private String taskId;

	private String taskName = CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN) ? "Current test instance" : "当前测试实例";

	private String mpSceneId;

	public void setResourceId(String resourceId) {
		this.taskId = resourceId;
	}

}
