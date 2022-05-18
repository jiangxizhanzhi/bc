package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.context.FlowListContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.FlowInfoContext;
import com.cyberpeace.integratation.model.scene.FlowRecordContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class EvalSceneServiceImpl implements EvalSceneService {

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Override
	public SceneViewContext viewScene(String mpSceneId, String topoId) {

		SceneViewContext ret = new SceneViewContext();
		String currentUserId = UserInfoContext.getUserContext().getUserId();

		// 尝试获取启动的远程场景
		if (mpSceneId != null) {
			JSONObject sceneData = localSceneService.getSceneData(mpSceneId, currentUserId);
			if (sceneData != null) {
				ret.setSceneData(sceneData);
				ret.setMpSceneId(mpSceneId);
				return ret;
			}
		}

		if (topoId != null) {
			JSONObject topoData = localSceneService.getTopoData(topoId)
					.getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
			ret.setTopoId(topoId);
			ret.setTopoData(topoData);
			return ret;

		}

		return null;
	}

	@Override
	public Boolean stopScene(LocalScene localScene) {

		Boolean stopResult = localSceneService.stopScene(localScene);

		// 停止场景失败时的处理
		if (!stopResult) {
			throw new BusinessException(ErrorCode.FAILED_TO_STOP_SCENE);
		}

		return true;
	}

	@Override
	public ListRsp<FlowElementFlow> getFlowList(FlowListContext flowListContext) {

		EvalTask evalTask = flowListContext.getEvalTask();
		String nodeId = flowListContext.getNodeId();
		Integer interactiveType = flowListContext.getInteractiveType();

		List<FlowElementFlow> nodeFlows = getTaskFlows(evalTask, nodeId);

		// 按照流量类型过滤数据
		if (interactiveType != null) {
			nodeFlows.removeIf(flowElementFlow -> !flowElementFlow.getInteractiveType()
					.equals(interactiveType));
		}

		// 切片
		ListRsp<FlowElementFlow> flowListRsp = PageUtil.getListResult(nodeFlows,
				flowListContext.getPage(), nodeFlows.size());

		FlowRecordContext flowRecordContext = new FlowRecordContext();
		flowRecordContext.setId(evalTask.getMpSceneId());
		flowRecordContext.setNodeId(nodeId);

		// 逻辑优化 todo
		Integer status;
		try {
			JSONObject jsonObject = remoteSceneService.getFlowRecords(flowRecordContext);
			status = jsonObject.getInteger("status");
		} catch (Exception e) {
			status = 4;
		}

		Integer commonStatus;
		if (status.equals(3) || status.equals(4)) {
			commonStatus = 0;
		} else {
			commonStatus = 1;
		}

		for (FlowElementFlow flowElementFlow : flowListRsp.getList()) {
			flowElementFlow.setCommonStatus(commonStatus);
		}

		// 5. 返回流量数据列表
		return flowListRsp;
	}

	@Override
	public List<FlowInfoContext> getAllFlow(EvalTask evalTask, String nodeId) {

		List<FlowElementFlow> nodeFlows = getTaskFlows(evalTask, nodeId);

		List<FlowInfoContext> flowInfoContexts = Lists.newArrayList();

		for (FlowElementFlow flowElementFlow : nodeFlows) {

			FlowInfoContext flowInfoContext = BeanUtils.copy(flowElementFlow,
					FlowInfoContext.class);
			flowInfoContext.setFlowId(flowElementFlow.getId());
			flowInfoContexts.add(flowInfoContext);
		}

		return flowInfoContexts;
	}

	private List<FlowElementFlow> getTaskFlows(EvalTask evalTask, String nodeId) {

		// 1.创建变量
		String userId = UserInfoContext.getUserContext().getUserId();
		List<Flow> flows = Lists.newArrayList();
		Cpflow cpflow;
		Map<String, String> elementsMap = Maps.newHashMap();

		// 2. 获取topo
		LocalScene localScene = localSceneService.getByBusinessInstanceId(evalTask.getResourceId());
		// 2.1 场景已启动
		if (localScene != null) {
			// 2.1.1 请求sceneData
			JSONObject sceneData = localSceneService.getSceneData(localScene, userId);
			// 2.1.2 转换JsonObject为SceneData对象
			SceneData data = sceneData.toJavaObject(SceneData.class);
			// 2.1.3 获取流量数据
			cpflow = data.getPlugins().getCpflow();
			// 2.1.4 生成节点数据map
			List<SceneElement> elements = data.getTopology().getElements();
			for (SceneElement element : elements) {
				elementsMap.put(element.getId(), element.getName());
			}
		} else {
			// 2.2 场景未启动
			// 2.2.1 请求topoData
			JSONObject topoData = localSceneService.getTopoData(evalTask.getCopyedTopoId());
			TopoData data = topoData.getJSONObject(CrCommonConstants.TOPO_DATA_KEY)
					.toJavaObject(TopoData.class);
			cpflow = data.getPlugins().getCpflow();

			List<TopoElement> elements = data.getTopology().getElements();
			for (TopoElement element : elements) {
				elementsMap.put(element.getId(), element.getName());
			}
		}

		FlowElement flowElement = cpflow.getElements().get(nodeId);

		List<FlowElementFlow> nodeFlows = Lists.newArrayList();

		if (flowElement != null) {
			nodeFlows = flowElement.getFlows();
		}

		return nodeFlows;
	}

}
