package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.FlowElementFlow;
import com.cyberpeace.cr.evaluation.controller.rsp.ListFlowRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cs.dao.entity.LocalScene;

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

	ListFlowRsp<FlowElementFlow> getFlowList(EvalTask evalTask, String nodeId, Page page);

}
