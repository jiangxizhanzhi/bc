package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cs.dao.entity.LocalScene;

import lombok.Data;

/**
 * eval task ops 上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalTaskOpsContext {

	/**
	 * 当地场景
	 *
	 * @see LocalScene
	 */
	private LocalScene localScene;

	/**
	 * 评估任务
	 *
	 * @see EvalTask
	 */
	private EvalTask evalTask;

}
