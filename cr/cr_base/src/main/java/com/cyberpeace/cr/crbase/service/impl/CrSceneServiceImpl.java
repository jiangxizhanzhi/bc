package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.scene.AssetProperty;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsIdentifier;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.crbase.service.context.scene.FlowInfoContext;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateWsBo;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.FlowUpdateContext;
import com.cyberpeace.cs.service.context.IdNameType;
import com.cyberpeace.cs.service.context.scene.*;
import com.cyberpeace.integratation.model.mp.MpFlow;
import com.cyberpeace.integratation.model.scene.*;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 靶场场景Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Slf4j
@Service
public class CrSceneServiceImpl implements CrSceneService {

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 靶场角色Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * Web 套接字处理程序
	 */
	@Autowired
	private MyWebSocketHandler webSocketHandler;

	/**
	 * 保存拓扑
	 *
	 * @param sceneConfigData 场景配置数据
	 * @param businessCode    业务代号
	 * @param topoId          拓扑id
	 * @return {@code String }
	 */
	@Override
	public String saveTopo(SceneConfigData sceneConfigData, LocalScene.BusinessCode businessCode,
			String topoId) {
		JSONObject topoJson = new JSONObject();
		topoJson.put("scene_config", sceneConfigData.getSceneConfig());
		topoJson.put("logo", sceneConfigData.getLogo());
		topoJson.put("reference_type", businessCode.getCode());

		if (topoId != null) {
			topoJson.put("id", topoId);
		}

		return remoteTopoService.save(topoJson);
	}

	/**
	 * 获取流量文件路径
	 *
	 * @param flowId 流量id
	 * @return {@code String }
	 */
	@Override
	public String getFlowFilePath(String flowId) {
		MpFlow mpFlow = remoteSceneService.getFlowById(flowId);

		if (mpFlow == null) {
			log.error("Flow does not exist! flowId = [{}]", flowId);
			throw new BusinessException(CrBusinessErrorCode.FLOW_NOT_EXIST);
		}

		Integer resourceType = mpFlow.getResourceType();
		return resourceType.equals(MpFlow.resourceTypeEnum.PCAP.getCode())
				? mpFlow.getPcapFile()
				: mpFlow.getScriptFile();
	}

	/**
	 * 获取节点流量上下文列表
	 *
	 * @param mpSceneId 中台场景id
	 * @param topoId    拓扑id
	 * @param nodeId    节点id
	 * @return {@code List<FlowInfo> }
	 */
	@Override
	public List<FlowInfo> getNodeFlowContextList(String mpSceneId, String topoId, String nodeId) {
		List<ElementFlow> elementFlowList = getNodeFlowList(mpSceneId, topoId, nodeId);
		return BeanUtils.deepCopyList(elementFlowList, FlowInfo.class);
	}

	/**
	 * 获取节点流量列表
	 *
	 * @param mpSceneId 中台场景id
	 * @param topoId    拓扑id
	 * @param nodeId    节点id
	 * @return {@code List<ElementFlow> }
	 */
	@Override
	public List<ElementFlow> getNodeFlowList(String mpSceneId, String topoId, String nodeId) {

		if (StringUtils.isEmpty(nodeId)) {
			log.error("No node is specified! nodeId is empty");
			throw new BusinessException(CrBusinessErrorCode.NOT_SPECIFY_NODE);
		}

		CpFlow cpFlow = getCpFlow(mpSceneId, topoId);

		List<ElementFlow> nodeFlowList = Lists.newArrayList();

		if (cpFlow == null) {
			return nodeFlowList;
		}

		ElementFlowList elementFlowList = cpFlow.getElements().get(nodeId);

		if (elementFlowList != null) {
			nodeFlowList = elementFlowList.getFlows();
		}

		return nodeFlowList;
	}

	/**
	 * 获取cpFlow
	 *
	 * @param mpSceneId 中台场景id
	 * @param topoId    拓扑id
	 * @return {@code CpFlow }
	 */
	private CpFlow getCpFlow(String mpSceneId, String topoId) {

		// 1.创建变量
		UserInfo userInfo = UserInfoContext.getUserContext();
		String userId = userInfo == null ? null : userInfo.getUserId();
		CpFlow cpFlow = new CpFlow();

		// 2. 获取cpFlow
		// 2.1 优先获取场景数据
		if (StringUtils.isNotEmpty(mpSceneId)) {
			// 2.1.1 请求sceneData
			JSONObject data = localSceneService.getSceneData(mpSceneId, userId);
			// 2.1.2 转换JsonObject为SceneData对象
			SceneData sceneData = data.toJavaObject(SceneData.class);
			// 2.1.3 获取流量数据
			cpFlow = sceneData.getPlugins().getCpFlow();
		} else if (StringUtils.isNotEmpty(topoId)) {
			// 2.2 获取拓扑数据
			// 2.2.1 请求topoData
			JSONObject data = localSceneService.getTopoData(topoId);
			TopoData topoData = data.getJSONObject(CrCommonConstants.TOPO_DATA_KEY)
					.toJavaObject(TopoData.class);
			cpFlow = topoData.getPlugins().getCpFlow();
		}

		return cpFlow;
	}

	/**
	 * 过滤流量列表
	 *
	 * @param elementFlowList 元素流量列表
	 * @param interactiveType 交互式类型
	 * @param page            分页
	 * @return {@code ListRsp<ElementFlow> }
	 */
	private ListRsp<ElementFlow> filterFlowList(List<ElementFlow> elementFlowList,
			Integer interactiveType, Page page) {

		// 按照流量类型过滤数据
		if (interactiveType != null) {
			elementFlowList.removeIf(
					elementFlow -> !elementFlow.getInteractiveType().equals(interactiveType));
		}

		// 分页
		return PageUtil.getListResult(elementFlowList, page, elementFlowList.size());
	}

	/**
	 * 设置元素流量状态
	 *
	 * @param elementFlowList 元素流量列表
	 * @param mpSceneId       中台场景id
	 * @param nodeId          节点id
	 * @return {@code List<ElementFlow> }
	 */
	@Override
	public List<ElementFlow> setElementFlowStatus(List<ElementFlow> elementFlowList,
			String mpSceneId, String nodeId) {

		Integer taskStatus = getFlowTaskStatus(mpSceneId, nodeId);

		for (ElementFlow elementFlow : elementFlowList) {
			elementFlow.setCommonStatus(taskStatus);
		}

		return elementFlowList;
	}

	/**
	 * 获取资产列表
	 *
	 * @param req           请求体
	 * @param withHostState 使用主机状态
	 * @return {@code List<JSONObject> }
	 */
	@Override
	public List<JSONObject> getAssetList(AssetPageReq req, Boolean withHostState) {
		String roomId = req.getResource_id();
		String userId = UserInfoContext.getUserContext().getUserId();
		// make req
		SceneNodeListReq listReq = new SceneNodeListReq();
		String mpSceneId = localSceneService.getMpSceneIdByBusinessInstanceId(roomId);
		listReq.setId(mpSceneId);
		listReq.setName(req.getPattern());
		listReq.setSystemTypeName(req.getSystemTypeName());
		listReq.setRealTimeHostState(withHostState);
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		// get role assets
		if (role.isRvbStudent() || role.isTeamStudent()) {
			// if student
			CrRoleNodesData nodesData = JsonUtil.toJsonObject(role.getNodes(),
					CrRoleNodesData.class);
			List<String> assetNodeIds = nodesData.getAssetNodes().stream().map(CrRoleNode::getId)
					.collect(Collectors.toList());
			if (assetNodeIds.isEmpty()) {
				// 没有资产，直接返回空列表
				return Lists.newArrayList();
			} else {
				// 有资产，传入节点id
				listReq.setElementIds(assetNodeIds);
			}
		}
		return localSceneService.getAssetList(listReq);
	}

	/**
	 * 获取操作系统名字
	 *
	 * @param roomId 房间id
	 * @return {@code List<IdNameType> }
	 */
	@Override
	public List<IdNameType> getOsNames(String roomId) {
		List<JSONObject> allAssets = localSceneService.getAllAssetByBusinessInstanceId(roomId);
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (role.isRvbStudent() || role.isTeamStudent()) {
			CrRoleNodesData nodesData = JsonUtil.toJsonObject(role.getNodes(),
					CrRoleNodesData.class);
			List<String> assetNodeIds = nodesData.getAssetNodes().stream().map(CrRoleNode::getId)
					.collect(Collectors.toList());
			if (assetNodeIds.isEmpty()) {
				// 没有资产，直接返回空列表
				return Lists.newArrayList();
			} else {
				// 过滤非资产节点
				allAssets
						.removeIf(item -> !assetNodeIds.contains(item.getString(AssetProperty.ID)));
			}
		}
		return CrCommonUtil.getOsListFromAssets(allAssets);
	}

	/**
	 * 发送场景更新场景WS
	 *
	 * @param businessId 业务id
	 */
	@Override
	public void sendSceneUpdateSceneWs(String businessId) {
		SceneUpdateWsBo bo = new SceneUpdateWsBo(SceneUpdateWsBo.SceneUpdateType.ASSET_UPDATED);
		sendWsData(bo, businessId);
	}

	/**
	 * 发送场景停消息
	 *
	 * @param businessId 业务id
	 */
	@Override
	public void sendSceneStopMessage(String businessId) {
		SceneUpdateWsBo bo = new SceneUpdateWsBo(SceneUpdateWsBo.SceneUpdateType.SCENE_STOPPED);
		sendWsData(bo, businessId);
	}

	/**
	 * 发送节点删除场景WS
	 *
	 * @param businessId 业务id
	 */
	@Override
	public void sendNodeDeleteSceneWs(String businessId) {
		SceneUpdateWsBo bo = new SceneUpdateWsBo(SceneUpdateWsBo.SceneUpdateType.NODE_DELETED);
		sendWsData(bo, businessId);
	}

	/**
	 * 发送ws数据
	 *
	 * @param data       数据
	 * @param businessId 业务标识
	 */
	private <T> void sendWsData(T data, String businessId) {
		MissionWsIdentifier identifier = new MissionWsIdentifier();
		identifier.setBusinessId(businessId);
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		identifier.setPath(WsPath.CR_SCENE);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		webSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 启动流量前校验流量任务状态
	 *
	 * @param mpSceneId 中台场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean }
	 */
	private Boolean checkFlowTaskStatus(String mpSceneId, String nodeId) {

		Integer taskStatus = getFlowTaskStatus(mpSceneId, nodeId);

		if (taskStatus.equals(ElementFlow.TaskStatusEnum.UNDEFINED.getCode())) {
			log.error("Failed to judge flow task status! mpSceneId = [{}], nodeId = [{}]", mpSceneId, nodeId);
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_JUDGE_FLOW_TASK_STATUS);
		}

		if (!taskStatus.equals(ElementFlow.TaskStatusEnum.STOPPED.getCode())
				&& !taskStatus.equals(ElementFlow.TaskStatusEnum.ZERO.getCode())) {
			log.error("Uncompleted flow task exist on the node! mpSceneId = [{}], nodeId = [{}]", mpSceneId, nodeId);
			throw new BusinessException(CrBusinessErrorCode.FLOW_TASK_EXISTS);
		}

		return true;
	}

	/**
	 * 获取节点流量任务状态
	 *
	 * @param mpSceneId 中台场景id
	 * @param nodeId    节点id
	 * @return {@code Integer }
	 */
	private Integer getFlowTaskStatus(String mpSceneId, String nodeId) {

		FlowRecordContext flowRecordContext = new FlowRecordContext(mpSceneId, nodeId, null);

		// 判断流量状态
		Integer taskStatus;

		// 设置流量任务查询接口5秒超时
		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<JSONObject> task = () -> remoteSceneService.getFlowRecords(flowRecordContext);
		Future<JSONObject> future = executor.submit(task);

		try {
			JSONObject jsonObject = future.get(20, TimeUnit.SECONDS);
			taskStatus = (Integer) jsonObject.getOrDefault("status",
					ElementFlow.TaskStatusEnum.ZERO.getCode());
		} catch (Exception e) {
			taskStatus = ElementFlow.TaskStatusEnum.UNDEFINED.getCode();
		}

		return taskStatus;
	}

	/**
	 * 获取靶场流量列表
	 *
	 * @param context 上下文
	 * @return {@code ListRsp<ElementFlow> }
	 */
	@Override
	public ListRsp<ElementFlow> getCrFlowList(ElementFlowListContext context) {

		String mpSceneId = context.getMpSceneId();
		String nodeId = context.getNodeId();
		Integer interactiveType = context.getInteractiveType();

		List<ElementFlow> elementFlowList = getNodeFlowList(mpSceneId, context.getTopoId(), nodeId);

		// 按照条件过滤并分页
		return filterFlowList(elementFlowList, interactiveType, context.getPage());
	}

	/**
	 * Update Flow 参数
	 *
	 * @param flowOpsContext 流量操作上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean updateFlowParam(FlowOpsContext flowOpsContext) {

		CrCommonValidator.validateIp(flowOpsContext.getSourceIp());

		MpFlow mpFlow = remoteSceneService.getFlowById(flowOpsContext.getFlowId());

		if (!mpFlow.getInteractiveType().equals(MpFlow.interactiveTypeEnum.BACKGROUND.getCode())) {
			log.error("Non-background flow is not allowed to modify the source ip! mpFlowId = [{}]", flowOpsContext.getFlowId());
			throw new BusinessException(
					CrBusinessErrorCode.NON_BACKGROUND_FLOW_NOT_ALLOWED_TO_MODIFY_SOURCE_IP);
		}

		Boolean result = remoteSceneService.updateFlowParam(flowOpsContext);

		if (!result) {
			log.error("Failed to update the flow! flowOpsContext = [{}]", JsonUtil.toJsonString(flowOpsContext));
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_UPDATE_FLOW);
		}

		return result;
	}

	/**
	 * 启动流量
	 *
	 * @param mpSceneId  中台场景id
	 * @param nodeId     节点id
	 * @param flows      流量
	 * @param businessId 业务id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean startFlows(String mpSceneId, String nodeId, List<FlowInfoContext> flows,
			String businessId) {

		// 校验节点下流量任务状态
		// checkFlowTaskStatus(mpSceneId, nodeId);

		for (FlowInfoContext flowContext : flows) {
			FlowOpsContext flowOpsContext = BeanUtils.copy(flowContext,
					com.cyberpeace.integratation.model.scene.FlowOpsContext.class);
			flowOpsContext.setId(mpSceneId);
			flowOpsContext.setNodeId(nodeId);

			// 设置实例id
			SceneMpContext context = new SceneMpContext();
			context.getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID, businessId);
			FlowUpdateContext flowUpdateContext = new FlowUpdateContext(mpSceneId, nodeId,
					Lists.newArrayList(flowContext.getFlowId()));
			context.getPass_through().put(WebConstant.PASS_THROUGH_EXTENSION,
					JsonUtil.toJsonString(flowUpdateContext));
			flowOpsContext.setContext(context);

			Boolean result = remoteSceneService.startFlow(flowOpsContext);

			if (!result) {
				log.error("Failed to start flow! flowOpsContext = [{}]", JsonUtil.toJsonString(flowContext));
				throw new BusinessException(CrBusinessErrorCode.START_FLOW_FAILURE);
			}
		}

		return true;
	}

	/**
	 * 启动所有流量程
	 *
	 * @param mpSceneId  中台场景id
	 * @param nodeId     节点id
	 * @param runType    运行类型
	 * @param businessId 业务id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean startAllFlows(String mpSceneId, String nodeId, Integer runType,
			String businessId) {

		// 校验节点下流量任务状态
		// checkFlowTaskStatus(mpSceneId, nodeId);

		FlowBatchOpsContext flowBatchOpsContext = new FlowBatchOpsContext(mpSceneId, nodeId,
				runType);

		// 设置实例id
		SceneMpContext context = new SceneMpContext();
		context.getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID, businessId);
		FlowUpdateContext flowUpdateContext = new FlowUpdateContext(mpSceneId, nodeId, null);
		context.getPass_through().put(WebConstant.PASS_THROUGH_EXTENSION,
				JsonUtil.toJsonString(flowUpdateContext));
		flowBatchOpsContext.setContext(context);

		Boolean result = remoteSceneService.batchStartFlow(flowBatchOpsContext);

		if (!result) {
			log.error("Failed to start flow! flowOpsContext = [{}]", JsonUtil.toJsonString(flowBatchOpsContext));
			throw new BusinessException(CrBusinessErrorCode.START_FLOW_FAILURE);
		}

		return true;
	}
}
