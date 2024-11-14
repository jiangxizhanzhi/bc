package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cs.dao.entity.LocalScene;

import lombok.Data;

/**
 * 测评任务操作上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTaskOpsContext {

	/**
	 * 当地场景
	 */
	private LocalScene localScene;

	/**
	 * 测评任务
	 */
	private EvalTask evalTask;

}
