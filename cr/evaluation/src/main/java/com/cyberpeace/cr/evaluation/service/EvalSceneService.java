package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.FlowElementFlow;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.context.FlowListContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.FlowInfoContext;

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

	ListRsp<FlowElementFlow> getFlowList(FlowListContext flowListContext);

	List<FlowInfoContext> getAllFlow(EvalTask evalTask, String nodeId);

}
