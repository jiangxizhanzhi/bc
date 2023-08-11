package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.context.scene.ElementFlow;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.crbase.service.context.scene.FlowInfoContext;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cs.service.context.IdNameType;
import com.cyberpeace.integratation.model.scene.FlowOpsContext;

/**
 *
 */
public interface CrSceneService {

	/** 保存、更新拓扑
	 * @param sceneConfigData 场景配置数据
	 * @param businessCode 训练类型
	 * @param topoId 拓扑id
	 * @return 拓扑id
	 */
	String saveTopo(SceneConfigData sceneConfigData, BusinessCode businessCode,
			String topoId);

	/** 获取流量文件路径
	 * @param flowId 流量id
	 * @return 流量文件路径
	 */
	String getFlowFilePath(String flowId);

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

	/**
	 * 获取资产列表
	 * 不做分页
	 *
	 * @param req               请求体
	 * @param withHostState 实时主机状态
	 * @return {@code List<JSONObject>}
	 */
	List<JSONObject> getAssetList(AssetPageReq req, Boolean withHostState);

	/**
	 * 获取操作系统名字
	 *
	 * @param roomId 房间id
	 * @return {@code List<IdNameType>}
	 */
	List<IdNameType> getOsNames(String roomId);

	/**
	 * 发送场景动态更新ws
	 *
	 * @param businessId 业务标识
	 */
	void sendSceneUpdateSceneWs(String businessId);

	/**
	 * 发送场景停止消息
	 *
	 * @param businessId 业务标识
	 */
	void sendSceneStopMessage(String businessId);

	/**
	 * 发送节点动态删除ws
	 *
	 * @param businessId 业务标识
	 */
	void sendNodeDeleteSceneWs(String businessId);
}
