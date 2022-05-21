package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.context.scene.*;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.mp.MpFlow;
import com.cyberpeace.integratation.model.scene.FlowBatchOpsContext;
import com.cyberpeace.integratation.model.scene.FlowInfo;
import com.cyberpeace.integratation.model.scene.FlowOpsContext;
import com.cyberpeace.integratation.model.scene.FlowRecordContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class CrSceneServiceImpl implements CrSceneService {

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private RemoteSceneService remoteSceneService;

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

	@Override
	public String getFlowFilePath(String flowId) {
		MpFlow mpFlow = remoteSceneService.getFlowById(flowId);

		if (mpFlow == null) {
			throw new BusinessException(CrBusinessErrorCode.FLOW_NOT_EXIST);
		}

		Integer resourceType = mpFlow.getResourceType();
		String flowFilePath = resourceType.equals(MpFlow.resourceTypeEnum.PCAP.getCode())
				? mpFlow.getPcapFile()
				: mpFlow.getScriptFile();
		return flowFilePath;
	}

	@Override
	public List<FlowInfo> getNodeFlowContextList(String mpSceneId, String topoId, String nodeId) {
		List<ElementFlow> elementFlowList = getNodeFlowList(mpSceneId, topoId, nodeId);
		return BeanUtils.deepCopyList(elementFlowList, FlowInfo.class);
	}

	private List<ElementFlow> getNodeFlowList(String mpSceneId, String topoId, String nodeId) {

		if (StringUtils.isEmpty(nodeId)) {
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

	private CpFlow getCpFlow(String mpSceneId, String topoId) {

		// 1.创建变量
		String userId = UserInfoContext.getUserContext().getUserId();
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

	private ListRsp<ElementFlow> filterFlowList(List<ElementFlow> elementFlowList,
			Integer interactiveType, Page page) {

		// 按照流量类型过滤数据
		if (interactiveType != null) {
			elementFlowList.removeIf(
					elementFlow -> !elementFlow.getInteractiveType().equals(interactiveType));
		}

		// 分页
		ListRsp<ElementFlow> flowListRsp = PageUtil.getListResult(elementFlowList, page,
				elementFlowList.size());

		return flowListRsp;
	}

	private List<ElementFlow> setElementFlowStatus(List<ElementFlow> elementFlowList,
			String mpSceneId, String nodeId) {

		FlowRecordContext flowRecordContext = new FlowRecordContext(mpSceneId, nodeId, null);
		// 判断优化 todo
		Integer taskStatus;
		try {
			JSONObject jsonObject = remoteSceneService.getFlowRecords(flowRecordContext);
			taskStatus = (Integer) jsonObject.getOrDefault("status",
					ElementFlow.TaskStatusEnum.ZERO.getCode());
		} catch (Exception e) {
			taskStatus = ElementFlow.TaskStatusEnum.INIT.getCode();
		}

		for (ElementFlow elementFlow : elementFlowList) {
			elementFlow.setCommonStatus(taskStatus);
		}

		return elementFlowList;
	}

	/**启动流量前校验流量状态 todo*/
	private Boolean checkElementFlowStatus(String mpSceneId, String nodeId, String flowId) {
		FlowRecordContext flowRecordContext = new FlowRecordContext(mpSceneId, nodeId, flowId);
		JSONObject jsonObject = remoteSceneService.getFlowRecords(flowRecordContext);
		return true;
	}

	@Override
	public ListRsp<ElementFlow> getCrFlowList(ElementFlowListContext context) {

		String mpSceneId = context.getMpSceneId();
		String nodeId = context.getNodeId();
		Integer interactiveType = context.getInteractiveType();

		List<ElementFlow> elementFlowList = getNodeFlowList(mpSceneId, context.getTopoId(), nodeId);

		// 按照条件过滤并分页
		ListRsp<ElementFlow> flowListRsp = filterFlowList(elementFlowList, interactiveType,
				context.getPage());

		// 设置流量状态
		setElementFlowStatus(flowListRsp.getList(), mpSceneId, nodeId);

		return flowListRsp;
	}

	@Override
	public Boolean updateFlowParam(FlowOpsContext flowOpsContext) {
		Boolean result = remoteSceneService.updateFlowParam(flowOpsContext);

		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_UPDATE_FLOW);
		}

		return result;
	}

	@Override
	public Boolean startFlows(String mpSceneId, String nodeId, List<FlowInfoContext> flows) {
		for (FlowInfoContext flowContext : flows) {
			FlowOpsContext flowOpsContext = BeanUtils.copy(flowContext,
					com.cyberpeace.integratation.model.scene.FlowOpsContext.class);
			flowOpsContext.setId(mpSceneId);
			flowOpsContext.setNodeId(nodeId);
			Boolean result = remoteSceneService.startFlow(flowOpsContext);

			if (!result) {
				throw new BusinessException(CrBusinessErrorCode.START_FLOW_FAILURE);
			}
		}

		return true;
	}

	@Override
	public Boolean startAllFlows(String mpSceneId, String nodeId, Integer runType) {

		FlowBatchOpsContext flowBatchOpsContext = new FlowBatchOpsContext(mpSceneId, nodeId,
				runType);

		List<FlowInfo> flowInfoList = getNodeFlowContextList(mpSceneId, null, nodeId);

		if (flowInfoList.isEmpty()) {
			throw new BusinessException(CrBusinessErrorCode.NO_FLOW_EXISTS_ON_NODE);
		}

		flowBatchOpsContext.setFlows(flowInfoList);

		Boolean result = remoteSceneService.batchStartFlow(flowBatchOpsContext);

		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.START_FLOW_FAILURE);
		}

		return true;
	}
}
