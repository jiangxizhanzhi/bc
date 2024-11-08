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
 * @date 2024/11/08
 */
public interface EvalSceneService {

	/**
	 * 查看场景
	 *
	 * @param mpSceneId
	 * @param topoId
	 * @return
	 */
	SceneViewContext viewScene(String mpSceneId, String topoId);

	/**
	 * 关闭场景
	 *
	 * @param localScene
	 * @return
	 */
	Boolean stopScene(LocalScene localScene);

	/**
	 * 构造场景流量列表查询参数
	 *
	 * @param evalTaskFlowListQuery
	 * @param evalTask
	 * @return
	 */
	ElementFlowListContext getElementFlowListContext(EvalTaskFlowListQuery evalTaskFlowListQuery,
			EvalTask evalTask);

}
