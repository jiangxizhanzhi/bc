package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.crbase.service.context.scene.FlowInfoContext;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.scene.ElementFlow;
import com.cyberpeace.integratation.model.scene.FlowInfo;
import com.cyberpeace.integratation.model.scene.FlowOpsContext;

/**
 * cr场景服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface CrSceneService {

	/** 保存、更新拓扑
	 * @param sceneConfigData 场景配置数据
	 * @param businessCode 训练类型
	 * @param topoId 拓扑id
	 * @return 拓扑id
	 */
	String saveTopo(SceneConfigData sceneConfigData, LocalScene.BusinessCode businessCode,
			String topoId);

	/** 获取流量文件路径
	 * @param flowId 流量id
	 * @return 流量文件路径
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
	 * @param elementFlowListContext 节点流量查询体
	 * @return 节点流量列表
	 */
	ListRsp<ElementFlow> getCrFlowList(ElementFlowListContext elementFlowListContext);

	/** 更新流量参数
	 * @param flowOpsContext 流量操作上下文
	 * @return 布尔值
	 */
	Boolean updateFlowParam(FlowOpsContext flowOpsContext);

	/** 启动流量
	 * @param mpSceneId 中台场景id
	 * @param nodeId 节点id
	 * @param flows 流量列表
	 * @return 启动结果（布尔值）
	 */
	Boolean startFlows(String mpSceneId, String nodeId, List<FlowInfoContext> flows,
			String businessId);

	/** 启动场景节点下所有流量
	 * @param mpSceneId 中台场景id
	 * @param nodeId 节点id
	 * @param runType 启动类型
	 * @return 启动结果（布尔值）
	 */
	Boolean startAllFlows(String mpSceneId, String nodeId, Integer runType, String businessId);

	/** 获取节点流量列表
	 * @param mpSceneId 中台场景id
	 * @param topoId 拓扑id
	 * @param nodeId 节点id
	 * @return 节点流量列表
	 */
	List<ElementFlow> getNodeFlowList(String mpSceneId, String topoId, String nodeId);

	/** 设置节点流量状态
	 * @param elementFlowList 节点流量列表数据
	 * @param mpSceneId 中台场景id
	 * @param nodeId 节点id
	 * @return 节点流量列表
	 */
	List<ElementFlow> setElementFlowStatus(List<ElementFlow> elementFlowList, String mpSceneId,
			String nodeId);

}
