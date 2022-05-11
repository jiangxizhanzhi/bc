package com.cyberpeace.cr.evaluation.service;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;

/**
 * 测试靶场-场景管理
 */
public interface EvalSceneService {

	/**
	 * 查看场景
	 * @return
	 */
	SceneViewContext viewScene(String mpSceneId, String topoId);

}
