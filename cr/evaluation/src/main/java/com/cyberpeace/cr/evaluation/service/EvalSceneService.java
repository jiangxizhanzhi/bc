package com.cyberpeace.cr.evaluation.service;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskFlowListQuery;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 测试靶场-场景管理
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface EvalSceneService {

	/**
	 * 查看场景
	 *
	 * @param mpSceneId
	 * @param topoId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see SceneViewContext
	 */
	SceneViewContext viewScene(String mpSceneId, String topoId);

	/**
	 * 关闭场景
	 *
	 * @param localScene
	 * @return
	 * @throws
	 * @see LocalScene
	 * @see Boolean
	 */
	Boolean stopScene(LocalScene localScene);

	/**
	 * 构造场景流量列表查询参数
	 *
	 * @param evalTaskFlowListQuery
	 * @param evalTask
	 * @return
	 * @throws
	 * @see EvalTaskFlowListQuery
	 * @see EvalTask
	 * @see ElementFlowListContext
	 */
	ElementFlowListContext getElementFlowListContext(EvalTaskFlowListQuery evalTaskFlowListQuery,
			EvalTask evalTask);

}
