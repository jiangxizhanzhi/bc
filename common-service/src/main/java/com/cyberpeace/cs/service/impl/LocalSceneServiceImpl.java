package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.constant.Operator;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.CreateSnapshotContext;
import com.cyberpeace.cs.service.context.RenameTopoContext;
import com.cyberpeace.cs.service.context.SceneFlowContext;
import com.cyberpeace.cs.service.context.TopoSwitchRecordingContext;
import com.cyberpeace.cs.service.context.scene.CpFlow;
import com.cyberpeace.cs.service.context.scene.ElementFlow;
import com.cyberpeace.cs.service.context.scene.ElementFlowList;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.integratation.model.scene.*;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SN
 */
@Slf4j
@Service
public class LocalSceneServiceImpl extends ServiceImpl<LocalSceneMapper, LocalScene> implements LocalSceneService {

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Override
	public LocalScene getByBusinessInstanceId(String businessInstanceId) {

        return getBaseMapper().getByBusinessInstanceId(businessInstanceId);
	}

	@Override
	public String startScene(LocalScene localScene) {
		if (localScene.getRecord() != null) {
			switchRecording(localScene.getTopoId(), localScene.getRecord());
		}

		SceneStartContext startContext = getSceneStartContext(localScene);

		return remoteSceneService.startScene(startContext);
	}

	@Override
	public String testScene(LocalScene localScene) {
		SceneStartContext startContext = getSceneStartContext(localScene);

		return remoteSceneService.testScene(startContext);
	}

	/**
	 * 生成场景启动的上下文
	 * @param localScene
	 * @return
	 */
	private SceneStartContext getSceneStartContext(LocalScene localScene) {
		SceneStartContext context = new SceneStartContext();

		context.setId(localScene.getTopoId());
		context.setSceneAttr(localScene.getSceneAttr());
		context.setSceneTtl(localScene.getSceneTtl());
		// 前端场景统计小球用到此数据
		context.setReferenceMetaData(getReferenceMetaData(localScene));
		// 收到mp回传到消息后，会使用此值寻找对应到业务
		context.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());
		context.getContext().setAccessUserId(localScene.getUserId());

		// 场景大json flag注入使用
		context.setCscene(
				JsonUtil.toJsonObject(localScene.getMeta(), LocalSceneMeta.class).getTopoData());
		return context;
	}

	private ReferenceMetaData getReferenceMetaData(LocalScene localScene) {
		ReferenceMetaData referenceMetaData = new ReferenceMetaData();

		referenceMetaData.setBusinessCode(localScene.getBusinessCode());
		referenceMetaData.setCreateUserId(localScene.getUserId());
		referenceMetaData.setBusinessName(localScene.getSceneName());
		// 解析meta
		LocalSceneMeta meta = JSON.parseObject(localScene.getMeta(), LocalSceneMeta.class);
		referenceMetaData.setBusinessId(meta.getAppResourceId());
		referenceMetaData.setFromWeb(meta.getIsWeb());

		return referenceMetaData;
	}

	@Override
	public LocalScene getById(String localSceneId) {

		return localSceneMapper.selectById(localSceneId);
	}

	@Override
	public Boolean stopScene(String localSceneId) {
		if (StringUtils.isBlank(localSceneId)) {
			return false;
		}
		LocalScene localScene = getById(localSceneId);
		if (null == localScene) {
			return false;
		}
		return stopScene(localScene);
	}

	@Override
	public Boolean stopScene(LocalScene localScene) {
		if (null == localScene) {
			return false;
		}
		SceneOpsContext sceneOpsContext = getSceneOpsContext(localScene);

		return remoteSceneService.stopScene(sceneOpsContext);
	}

	@Override
	public Boolean restartNode(String localSceneId, String nodeId) {
		LocalScene localScene = getById(localSceneId);
		if (localScene == null || StringUtils.isBlank(localScene.getMpSceneId())) {
			log.error("invalid localScene {}", localSceneId);
			throw new BusinessException(ErrorCode.FAILED_TO_RESTART_NODE);
		}
		NodeOpsContext nodeOpsContext = getNodeOpsContext(localScene, nodeId);
		Boolean result = remoteSceneService.restartNode(nodeOpsContext);
		if (!result) {
			log.error("failed to restart node [{}] in scene [{}]", nodeId,
					localScene.getMpSceneId());
			throw new BusinessException(ErrorCode.FAILED_TO_RESTART_NODE);
		}
		return true;
	}

	@Override
	public Boolean recreateNode(String localSceneId, String nodeId) {
		LocalScene localScene = getById(localSceneId);
		if (localScene == null || StringUtils.isBlank(localScene.getMpSceneId())) {
			log.error("invalid localScene {}", localSceneId);
			throw new BusinessException(ErrorCode.FAILED_TO_RECREATE_NODE);
		}
		NodeOpsContext nodeOpsContext = getNodeOpsContext(localScene, nodeId);
		Boolean result = remoteSceneService.recreateNode(nodeOpsContext);
		if (!result) {
			log.error("failed to recreate node [{}] in scene [{}]", nodeId,
					localScene.getMpSceneId());
			throw new BusinessException(ErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return true;
	}

	@Override
	public Boolean startFlow(LocalScene localScene, String nodeId, String flowId) {
		FlowOpsContext context = getFlowOpsContext(localScene, nodeId, flowId);
		Boolean result = remoteSceneService.startFlow(context);
		if (!result) {
			log.error("Failed to start flow [{}] of node [{}] in scene [{}]", flowId, nodeId,
					localScene.getMpSceneId());
			return false;
		}
		log.info(String.format("Start flow [%s] of node [%s] in scene [%s] successfully", flowId,
				nodeId, localScene.getMpSceneId()));
		return true;
	}

	@Override
	public Boolean stopFlow(LocalScene localScene, String nodeId, String flowId) {
		FlowOpsContext context = getFlowOpsContext(localScene, nodeId, flowId);
		Boolean result = remoteSceneService.stopFlow(context);
		if (!result) {
			log.error("failed to stop flow [{}] of node [{}] in scene [{}]", flowId, nodeId,
					localScene.getMpSceneId());
			return false;
		}
		return true;
	}

	@Override
	public Boolean parseFlow(LocalScene localScene, String nodeId, String flowId) {
		FlowOpsContext context = getFlowOpsContext(localScene, nodeId, flowId);
		Boolean result = remoteSceneService.parseFlow(context);
		if (!result) {
			log.error("failed to parse flow [{}] of node [{}] in scene [{}]", flowId, nodeId,
					localScene.getMpSceneId());
			return false;
		}
		return true;
	}

	@Override
	public Boolean resumeFlow(LocalScene localScene, String nodeId, String flowId) {
		FlowOpsContext context = getFlowOpsContext(localScene, nodeId, flowId);
		Boolean result = remoteSceneService.resumeFlow(context);
		if (!result) {
			log.error("Failed to resume flow [{}] of node [{}] in scene [{}]", flowId, nodeId,
					localScene.getMpSceneId());
			return false;
		}
		return true;
	}

	@Override
	public Map<String, SceneFlowContext> getSceneFlowList(SceneData sceneData,
			Integer interactiveType) {

		CpFlow cpFlow = sceneData.getPlugins().getCpFlow();
		Map<String, SceneFlowContext> sceneFlowContextMap = Maps.newHashMap();

		if (cpFlow == null) {
			return sceneFlowContextMap;
		}

		for (Map.Entry<String, ElementFlowList> entry : cpFlow.getElements().entrySet()) {
			String nodeId = entry.getKey();
			for (ElementFlow elementFlow : entry.getValue().getFlows()) {

				Integer flowInteractiveType = elementFlow.getInteractiveType();

				if (interactiveType != null && !flowInteractiveType.equals(interactiveType)) {
					continue;
				}

				String flowId = elementFlow.getId();
				SceneFlowContext sceneFlowContext = sceneFlowContextMap.getOrDefault(flowId,
						new SceneFlowContext(flowId, flowInteractiveType));
				sceneFlowContext.getNodeIds().add(nodeId);
				sceneFlowContextMap.put(flowId, sceneFlowContext);
			}

		}
		return sceneFlowContextMap;
	}

	@Override
	public Boolean startRandomFlow(String mpSceneId,
			Map<String, SceneFlowContext> sceneFlowContextMap) {

		LocalScene localScene = localSceneMapper.selectBySceneId(mpSceneId);

		// 业务已被删除或场景下无流量时，返回启动失败
		if (!sceneFlowContextMap.isEmpty() && localScene != null) {

			List<String> flowIds = Lists.newArrayList();
			flowIds.addAll(sceneFlowContextMap.keySet());

			String flowId = ListUtil.selectRandomValue(flowIds);
			String nodeId = ListUtil
					.selectRandomValue(sceneFlowContextMap.get(flowId).getNodeIds());
			return startFlow(localScene, nodeId, flowId);
		}

		return false;
	}

	@Override
	public Boolean pauseScene(String localSceneId) {
		LocalScene localScene = getById(localSceneId);
		// 1. 场景已暂停，返回true
		if (localScene.getSceneStatus().equals(LocalScene.SceneStatusEnum.PAUSE.getCode())) {
			return true;
		}
		// 2. 发送mp请求
		Boolean result = remoteSceneService.pauseScene(getSceneOpsContext(localScene));

		if (!result) {
			log.error("failed to pause scene [{}]", localScene.getMpSceneId());
			return false;
		}
		// 3. 修改本地数据
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.PAUSE.getCode());
		localScene.setPauseTime(new Date());
		updateById(localScene);

		return true;
	}

	@Override
	public Boolean unpauseScene(String localSceneId) {
		LocalScene localScene = getById(localSceneId);
		// 1、 确认场景处于暂停状态，可以恢复
		ensureScenePaused(localScene);
		// 2、 发送mp请求
		Boolean result = remoteSceneService.unpauseScene(getSceneOpsContext(localScene));

		if (!result) {
			log.error("failed to unpause scene [{}]", localScene.getMpSceneId());
			return false;
		}
		// 3、修改本地数据
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());

		if (localScene.getEndTime() != null) {
			// 计算暂停时间
			long seconds = TimeUtil.getIntervalSeconds(localScene.getPauseTime(), new Date());
			Date endTime = TimeUtil.addSeconds(localScene.getEndTime(), seconds);
			localScene.setEndTime(endTime);
		}
		updateById(localScene);

		return true;
	}

	private void ensureScenePaused(LocalScene localScene) {
		if (!localScene.getSceneStatus().equals(LocalScene.SceneStatusEnum.PAUSE.getCode())) {
			log.error("scene [{}] is not paused", localScene.getMpSceneId());
			throw new BusinessException(ErrorCode.NOT_PAUSED);
		}
	}

	@Override
	public String saveTopo(JSONObject topoData, Integer businessType) {
		topoData.put("reference_type", businessType);
		return remoteTopoService.save(topoData);
	}

	@Override
	public String saveTopo(JSONObject topoData, String topoId) {
		topoData.put("id", topoId);
		return remoteTopoService.save(topoData);
	}

	@Override
	public String setTopoType(String topoId, Integer referenceType) {
		JSONObject topoData = getTopoData(topoId);
		topoData.put("reference_type", referenceType);
		return remoteTopoService.saveUnlimited(topoData);
	}

	@Override
	public void deleteTopo(String topoId) {
		remoteTopoService.deleteById(topoId);
	}

	@Override
	public Boolean renameTopo(String topoId, String name) {
		RenameTopoContext context = new RenameTopoContext(topoId, name);
		return name.equals(
				remoteTopoService.sync(JsonUtil.java2JsonObject(context)).getString("name"));
	}

	@Override
	public Integer getNodeCount(LocalScene localScene) {
		if (null != localScene.getNodeCount()) {
			return localScene.getNodeCount();
		} else {
			return remoteTopoService.getTopoDigest(localScene.getTopoId()).getNodeCounts();
		}
	}

	@Override
	public Integer getNodeCount(String localSceneId) {
		LocalScene localScene = getById(localSceneId);
		return getNodeCount(localScene);
	}

	@Override
	public JSONObject getSceneData(LocalScene localScene, String userId) {
		if (StringUtils.isNotBlank(localScene.getMpSceneId())) {
			return getSceneData(localScene.getMpSceneId(), userId);
		}
		return null;
	}

	@Override
	public JSONObject getSceneData(String mpSceneId, String userId) {
		if (StringUtils.isBlank(mpSceneId)) {
			return null;
		}

		JSONObject scene = remoteSceneService.getById(mpSceneId, userId);
		if (scene != null) {
			return scene.getJSONObject("scene");
		}
		return null;
	}

	@Override
	public JSONObject getTopoData(String topoId) {
		JSONObject topo = remoteTopoService.getTopoById(topoId);
		if (null == topo) {
			log.info("failed to get topoData with topoId [{}]", topoId);
			return null;
		}
		return topo;
	}

	@Override
	public Boolean ensureSceneStarted(LocalScene localScene) {
		if (localScene == null) {
			return false;
		}
		return StringUtils.isNotBlank(localScene.getMpSceneId());
	}

	@Override
	public Boolean ensureSceneStarted(String localSceneId) {
		if (StringUtils.isBlank(localSceneId)) {
			return false;
		}
		LocalScene localScene = getById(localSceneId);
		return ensureSceneStarted(localScene);
	}

	@Override
	public void deleteRemoteScene(LocalScene localScene) {
		SceneOpsContext sceneOpsContext = getSceneOpsContext(localScene);
		remoteSceneService.stopScene(sceneOpsContext);
	}

	@Override
	public JSONArray getMonitorSessions(LocalScene localScene, String studentId) {
		if (null == localScene || StringUtils.isBlank(localScene.getMpSceneId())) {
			return new JSONArray();
		}
		return remoteSceneService.getMonitorSessions(localScene.getMpSceneId(), studentId);
	}

	@Override
	public Boolean nodeSnapshot(LocalScene localScene, CreateSnapshotContext shotContext) {
		NodeSnapshotContext snapshotContext = getNodeSnapshotContext(localScene, shotContext);

		return remoteSceneService.nodeSnapshot(snapshotContext);
	}

	@Override
	public JSONArray snapshotHistory(String sceneId, String imageId) {
		LocalScene localScene = localSceneMapper.selectBySceneId(sceneId);
		if (null == localScene) {
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		return remoteSceneService.snapshotHistory(imageId);
	}

	@Override
	public JSONObject mergeSnapshot(String sceneId, MergeSnapshotContext snapshotContext) {
		LocalScene localScene = localSceneMapper.selectBySceneId(sceneId);
		if (null == localScene) {
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		return remoteSceneService.mergeSnapshot(snapshotContext);
	}

	@Override
	public Boolean deleteSnapshot(String sceneId, String nodeId, String snapshotId) {
		LocalScene localScene = localSceneMapper.selectBySceneId(sceneId);
		if (null == localScene) {
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		SceneOpsContext sceneOpsContext = getSceneOpsContext(localScene);
		SnapshotDeleteContext deleteContext = BeanUtils.copy(sceneOpsContext,
				SnapshotDeleteContext.class);
		deleteContext.setNodeId(nodeId);
		deleteContext.setSnapshotId(snapshotId);

		return remoteSceneService.deleteSnapshot(deleteContext);
	}

	private NodeSnapshotContext getNodeSnapshotContext(LocalScene localScene,
			CreateSnapshotContext context) {
		SceneOpsContext opsContext = getSceneOpsContext(localScene);
		NodeSnapshotContext snapshotContext = BeanUtils.copy(opsContext, NodeSnapshotContext.class);
		org.springframework.beans.BeanUtils.copyProperties(context, snapshotContext);

		return snapshotContext;
	}

	private SceneOpsContext getSceneOpsContext(LocalScene localScene) {
		SceneOpsContext sceneOpsContext = new SceneOpsContext();
		sceneOpsContext.setId(localScene.getMpSceneId());
		sceneOpsContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());
		sceneOpsContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_OPERATOR,
				Operator.BC.getDesc());

		return sceneOpsContext;
	}

	private NodeOpsContext getNodeOpsContext(LocalScene localScene, String nodeId) {
		NodeOpsContext nodeOpsContext = new NodeOpsContext();
		nodeOpsContext.setId(localScene.getMpSceneId());
		nodeOpsContext.setNodeId(nodeId);
		nodeOpsContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());

		return nodeOpsContext;
	}

	private FlowOpsContext getFlowOpsContext(LocalScene localScene, String nodeId, String flowId) {
		FlowOpsContext flowOpsContext = new FlowOpsContext();
		flowOpsContext.setId(localScene.getMpSceneId());
		flowOpsContext.setFlowId(flowId);
		flowOpsContext.setNodeId(nodeId);
		flowOpsContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());

		return flowOpsContext;
	}

	/** 
	 * 启动场景之前，开关录屏
	 * @param topoId 
	 * @param enableRecording
	 */
	private void switchRecording(String topoId, Boolean enableRecording) {
		TopoSwitchRecordingContext context = new TopoSwitchRecordingContext(topoId,
				enableRecording);
		remoteTopoService.sync(JsonUtil.java2JsonObject(context));
	}
}
