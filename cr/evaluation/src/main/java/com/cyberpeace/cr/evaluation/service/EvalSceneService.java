package com.cyberpeace.cr.evaluation.service;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cs.dao.entity.LocalScene;

import java.util.List;

/**
 * 测试靶场-场景管理
 */
public interface EvalSceneService {

	/**
	 * 查看场景
	 * @return
	 */
	SceneViewContext viewScene(String mpSceneId, String topoId);

	/**
	 * 关闭场景
	 * @return
	 */
	Boolean stopScene(LocalScene localScene);

	List<Flow> getFlowList(EvalTask evalTask);

}
