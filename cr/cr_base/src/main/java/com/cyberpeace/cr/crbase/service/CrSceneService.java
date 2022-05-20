package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlow;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.crbase.service.context.scene.FlowInfoContext;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.FlowInfo;
import com.cyberpeace.integratation.model.scene.FlowOpsContext;

/**
 *
 */
public interface CrSceneService {

	String saveTopo(SceneConfigData sceneConfigData, LocalScene.BusinessCode businessCode,
			String topoId);

	String getFlowFilePath(String flowId);

	List<FlowInfo> getNodeFlowContextList(String mpSceneId, String topoId, String nodeId);

	ListRsp<ElementFlow> getCrFlowList(ElementFlowListContext elementFlowListContext);

	Boolean updateFlowParam(FlowOpsContext flowOpsContext);

	Boolean startFlows(String mpSceneId, String nodeId, List<FlowInfoContext> flows);

	Boolean startAllFlows(String mpSceneId, String nodeId, Integer runType);

}
