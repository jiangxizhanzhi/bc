package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cs.dao.entity.LocalScene;

import lombok.Data;

/**
 * eval task ops 上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalTaskOpsContext {

	/**
	 * 当地场景
	 */
	private LocalScene localScene;

	/**
	 * 评估任务
	 */
	private EvalTask evalTask;

}
