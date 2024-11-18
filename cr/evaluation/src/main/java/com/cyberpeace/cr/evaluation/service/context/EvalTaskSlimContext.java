package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import lombok.Data;

/**
 * Eval Task Slim 上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalTaskSlimContext {

	/**
	 * 任务id
	 *
	 * @see String
	 */
	private String taskId;

	/**
	 * 任务名称
	 *
	 * @see String
	 */
	private String taskName = CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN) ? "Current test instance" : "当前测试实例";

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	private String mpSceneId;

	public void setResourceId(String resourceId) {
		this.taskId = resourceId;
	}

}
