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

	/** 保存、更新拓扑
	 * @param sceneConfigData
	 * @param businessCode
	 * @param topoId
	 * @return
	 */
	String saveTopo(SceneConfigData sceneConfigData, LocalScene.BusinessCode businessCode,
			String topoId);

	/** 获取流量文件路径
	 * @param flowId
	 * @return
	 */
	String getFlowFilePath(String flowId);

	/** 获取场景节点下流量（mp格式）列表
	 * @param mpSceneId
	 * @param topoId
	 * @param nodeId
	 * @return
	 */
	List<FlowInfo> getNodeFlowContextList(String mpSceneId, String topoId, String nodeId);

	/** 获取靶场场景节点下流量列表
	 * @param elementFlowListContext
	 * @return
	 */
	ListRsp<ElementFlow> getCrFlowList(ElementFlowListContext elementFlowListContext);

	/** 更新流量参数
	 * @param flowOpsContext
	 * @return
	 */
	Boolean updateFlowParam(FlowOpsContext flowOpsContext);

	/** 启动流量
	 * @param mpSceneId
	 * @param nodeId
	 * @param flows
	 * @return
	 */
	Boolean startFlows(String mpSceneId, String nodeId, List<FlowInfoContext> flows, String businessId);

	/** 启动场景节点下所有流量
	 * @param mpSceneId
	 * @param nodeId
	 * @param runType
	 * @return
	 */
	Boolean startAllFlows(String mpSceneId, String nodeId, Integer runType, String businessId);

	/** 获取节点流量列表
	 * @param mpSceneId
	 * @param topoId
	 * @param nodeId
	 * @return
	 */
	List<ElementFlow> getNodeFlowList(String mpSceneId, String topoId, String nodeId);

}
