package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.service.RemoteUserService;
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
 * 当地场景Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see LocalSceneService
 * @since 1.0.0
 */
@Slf4j
@Service
public class LocalSceneServiceImpl extends ServiceImpl<LocalSceneMapper, LocalScene> implements LocalSceneService {

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 远程拓扑Service
	 *
	 * @see RemoteTopoService
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 当地场景映射
	 *
	 * @see LocalSceneMapper
	 */
	@Autowired
	private LocalSceneMapper localSceneMapper;

	/**
	 * 获取由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene }
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	@Override
	public LocalScene getByBusinessInstanceId(String businessInstanceId) {

		return getBaseMapper().getByBusinessInstanceId(businessInstanceId);
	}

	/**
	 * 开始场景
	 *
	 * @param localScene 当地场景
	 * @return {@code String }
	 * @throws
	 * @see LocalScene
	 * @see String
	 */
	@Override
	public String startScene(LocalScene localScene) {
		if (localScene.getRecord() != null) {
			switchRecording(localScene.getTopoId(), localScene.getRecord());
		}

		checkDemonstration();
		SceneStartContext startContext = getSceneStartContext(localScene);


		return remoteSceneService.startScene(startContext);
	}

	/**
	 * 测试场景
	 *
	 * @param localScene 当地场景
	 * @return {@code String }
	 * @throws
	 * @see LocalScene
	 * @see String
	 */
	@Override
	public String testScene(LocalScene localScene) {
		SceneStartContext startContext = getSceneStartContext(localScene);

		return remoteSceneService.testScene(startContext);
	}

	/**
	 * 生成场景启动的上下文
	 *
	 * @param localScene
	 * @return
	 * @throws
	 * @see LocalScene
	 * @see SceneStartContext
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

	/**
	 * 获取参考元数据
	 *
	 * @param localScene 当地场景
	 * @return {@code ReferenceMetaData }
	 * @throws
	 * @see LocalScene
	 * @see ReferenceMetaData
	 */
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

	/**
	 * 获取由id
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code LocalScene }
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	@Override
	public LocalScene getById(String localSceneId) {

		return localSceneMapper.selectById(localSceneId);
	}

	/**
	 * 停场景
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
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

	/**
	 * 停场景
	 *
	 * @param localScene 当地场景
	 * @return {@code Boolean }
	 * @throws
	 * @see LocalScene
	 * @see Boolean
	 */
	@Override
	public Boolean stopScene(LocalScene localScene) {
		checkDemonstration();
		if (null == localScene) {
			return false;
		}
		SceneOpsContext sceneOpsContext = getSceneOpsContext(localScene);

		return remoteSceneService.stopScene(sceneOpsContext);
	}

	/**
	 * 重启节点
	 *
	 * @param localSceneId 当地场景id
	 * @param nodeId       节点id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
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

	/**
	 * 重新创建节点
	 *
	 * @param localSceneId 当地场景id
	 * @param nodeId       节点id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
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

	/**
	 * 启动流量
	 *
	 * @param localScene 当地场景
	 * @param nodeId     节点id
	 * @param flowId     流量id
	 * @return {@code Boolean }
	 * @throws
	 * @see LocalScene
	 * @see String
	 * @see String
	 * @see Boolean
	 */
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

	/**
	 * 停止流量
	 *
	 * @param localScene 当地场景
	 * @param nodeId     节点id
	 * @param flowId     流量id
	 * @return {@code Boolean }
	 * @throws
	 * @see LocalScene
	 * @see String
	 * @see String
	 * @see Boolean
	 */
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

	/**
	 * 获取场景流量列表
	 *
	 * @param sceneData       场景数据
	 * @param interactiveType 交互式类型
	 * @return {@code Map<String, SceneFlowContext> }
	 * @throws
	 * @see SceneData
	 * @see Integer
	 * @see Map<String, SceneFlowContext>
	 */
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

	/**
	 * 启动随机流量
	 *
	 * @param mpSceneId           中台场景id
	 * @param sceneFlowContextMap 场景流量上下文地图
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Map<String, SceneFlowContext>
	 * @see Boolean
	 */
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

	/**
	 * 暂停场景
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean pauseScene(String localSceneId) {
		checkDemonstration();
		LocalScene localScene = getById(localSceneId);
		// 1. 场景已暂停，返回true
		return pauseScene(localScene);
	}

	/**
	 * 取消暂停场景
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean unpauseScene(String localSceneId) {
		checkDemonstration();
		LocalScene localScene = getById(localSceneId);
		// 1、 确认场景处于暂停状态，可以恢复
		return unpauseScene(localScene);
	}

	/**
	 * 确保场景暂停
	 *
	 * @param localScene 当地场景
	 * @throws
	 * @see LocalScene
	 */
	private void ensureScenePaused(LocalScene localScene) {
		if (!localScene.getSceneStatus().equals(LocalScene.SceneStatusEnum.PAUSE.getCode())) {
			log.error("scene [{}] is not paused", localScene.getMpSceneId());
			throw new BusinessException(ErrorCode.NOT_PAUSED);
		}
	}

	/**
	 * 保存拓扑
	 *
	 * @param topoData     拓扑数据
	 * @param businessType 业务类型
	 * @return {@code String }
	 * @throws
	 * @see JSONObject
	 * @see Integer
	 * @see String
	 */
	@Override
	public String saveTopo(JSONObject topoData, Integer businessType) {
		topoData.put("reference_type", businessType);
		return remoteTopoService.save(topoData);
	}

	/**
	 * 保存拓扑
	 *
	 * @param topoData 拓扑数据
	 * @param topoId   拓扑id
	 * @return {@code String }
	 * @throws
	 * @see JSONObject
	 * @see String
	 * @see String
	 */
	@Override
	public String saveTopo(JSONObject topoData, String topoId) {
		topoData.put("id", topoId);
		return remoteTopoService.save(topoData);
	}

	/**
	 * 设置拓扑类型
	 *
	 * @param topoId        拓扑id
	 * @param referenceType 参考类型
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 */
	@Override
	public String setTopoType(String topoId, Integer referenceType) {
		JSONObject topoData = getTopoData(topoId);
		topoData.put("reference_type", referenceType);
		return remoteTopoService.saveUnlimited(topoData);
	}

	/**
	 * 删除拓扑
	 *
	 * @param topoId 拓扑id
	 * @throws
	 * @see String
	 */
	@Override
	public void deleteTopo(String topoId) {
		remoteTopoService.deleteById(topoId);
	}

	/**
	 * 重命名拓扑
	 *
	 * @param topoId 拓扑id
	 * @param name   名称
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean renameTopo(String topoId, String name) {
		RenameTopoContext context = new RenameTopoContext(topoId, name);
		return name.equals(
				remoteTopoService.sync(JsonUtil.java2JsonObject(context)).getString("name"));
	}

	/**
	 * 获取节点计数
	 *
	 * @param localScene 当地场景
	 * @return {@code Integer }
	 * @throws
	 * @see LocalScene
	 * @see Integer
	 */
	@Override
	public Integer getNodeCount(LocalScene localScene) {
		if (null != localScene.getNodeCount()) {
			return localScene.getNodeCount();
		} else {
			return remoteTopoService.getTopoDigest(localScene.getTopoId()).getNodeCounts();
		}
	}

	/**
	 * 获取节点计数
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	@Override
	public Integer getNodeCount(String localSceneId) {
		LocalScene localScene = getById(localSceneId);
		return getNodeCount(localScene);
	}

	/**
	 * 获取场景数据
	 *
	 * @param localScene 当地场景
	 * @param userId     用户id
	 * @return {@code JSONObject }
	 * @throws
	 * @see LocalScene
	 * @see String
	 * @see JSONObject
	 */
	@Override
	public JSONObject getSceneData(LocalScene localScene, String userId) {
		if (StringUtils.isNotBlank(localScene.getMpSceneId())) {
			return getSceneData(localScene.getMpSceneId(), userId);
		}
		return null;
	}

	/**
	 * 获取场景数据
	 *
	 * @param mpSceneId 中台场景id
	 * @param userId    用户id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see String
	 * @see JSONObject
	 */
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

	/**
	 * 获取拓扑数据
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	@Override
	public JSONObject getTopoData(String topoId) {
		JSONObject topo = remoteTopoService.getTopoById(topoId);
		if (null == topo) {
			log.info("failed to get topoData with topoId [{}]", topoId);
			return null;
		}
		return topo;
	}

	/**
	 * 确保场景开始
	 *
	 * @param localScene 当地场景
	 * @return {@code Boolean }
	 * @throws
	 * @see LocalScene
	 * @see Boolean
	 */
	@Override
	public Boolean ensureSceneStarted(LocalScene localScene) {
		if (localScene == null) {
			return false;
		}
		return StringUtils.isNotBlank(localScene.getMpSceneId());
	}

	/**
	 * 确保场景开始
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean ensureSceneStarted(String localSceneId) {
		if (StringUtils.isBlank(localSceneId)) {
			return false;
		}
		LocalScene localScene = getById(localSceneId);
		return ensureSceneStarted(localScene);
	}

	/**
	 * 删除远程场景
	 *
	 * @param localScene 当地场景
	 * @throws
	 * @see LocalScene
	 */
	@Override
	public void deleteRemoteScene(LocalScene localScene) {
		checkDemonstration();
		SceneOpsContext sceneOpsContext = getSceneOpsContext(localScene);
		remoteSceneService.stopScene(sceneOpsContext);
	}

	/**
	 * 获取监控会话
	 *
	 * @param localScene 当地场景
	 * @param studentId  学员id
	 * @return {@code JSONArray }
	 * @throws
	 * @see LocalScene
	 * @see String
	 * @see JSONArray
	 */
	@Override
	public JSONArray getMonitorSessions(LocalScene localScene, String studentId) {
		if (null == localScene || StringUtils.isBlank(localScene.getMpSceneId())) {
			return new JSONArray();
		}
		return remoteSceneService.getMonitorSessions(localScene.getMpSceneId(), studentId);
	}

	/**
	 * 获取主机州列表
	 *
	 * @param mpSceneId 中台场景id
	 * @param nodeIds   节点id列表
	 * @return {@code List<MpHostState> }
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<MpHostState>
	 */
	@Override
	public List<MpHostState> getHostStateList(String mpSceneId, List<String> nodeIds) {
		ElementCommonContext context = new ElementCommonContext();
		context.setSceneId(mpSceneId);
		context.setElementIds(String.join(CommonConstant.CODE_COMMA, nodeIds));
		return remoteSceneService.getHostState(context);
	}

	/**
	 * 节点快照
	 *
	 * @param localScene  当地场景
	 * @param shotContext 镜头上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see LocalScene
	 * @see CreateSnapshotContext
	 * @see Boolean
	 */
	@Override
	public Boolean nodeSnapshot(LocalScene localScene, CreateSnapshotContext shotContext) {
		NodeSnapshotContext snapshotContext = getNodeSnapshotContext(localScene, shotContext);

		return remoteSceneService.nodeSnapshot(snapshotContext);
	}

	/**
	 * 快照历史记录
	 *
	 * @param sceneId 场景id
	 * @param imageId 图像id
	 * @return {@code JSONArray }
	 * @throws
	 * @see String
	 * @see String
	 * @see JSONArray
	 */
	@Override
	public JSONArray snapshotHistory(String sceneId, String imageId) {
		LocalScene localScene = localSceneMapper.selectBySceneId(sceneId);
		if (null == localScene) {
			log.error("localScene is null,sceneId =[{}]", sceneId);
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		return remoteSceneService.snapshotHistory(imageId);
	}

	/**
	 * 合并快照
	 *
	 * @param sceneId         场景id
	 * @param snapshotContext 快照上下文
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see MergeSnapshotContext
	 * @see JSONObject
	 */
	@Override
	public JSONObject mergeSnapshot(String sceneId, MergeSnapshotContext snapshotContext) {
		LocalScene localScene = localSceneMapper.selectBySceneId(sceneId);
		if (null == localScene) {
			log.error("localScene is null,sceneId =[{}]", sceneId);
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		return remoteSceneService.mergeSnapshot(snapshotContext);
	}

	/**
	 * 删除快照
	 *
	 * @param sceneId    场景id
	 * @param nodeId     节点id
	 * @param snapshotId 快照id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean deleteSnapshot(String sceneId, String nodeId, String snapshotId) {
		LocalScene localScene = localSceneMapper.selectBySceneId(sceneId);
		if (null == localScene) {
			log.error("localScene is null,sceneId =[{}]", sceneId);
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		SceneOpsContext sceneOpsContext = getSceneOpsContext(localScene);
		SnapshotDeleteContext deleteContext = BeanUtils.copy(sceneOpsContext,
				SnapshotDeleteContext.class);
		deleteContext.setNodeId(nodeId);
		deleteContext.setSnapshotId(snapshotId);

		return remoteSceneService.deleteSnapshot(deleteContext);
	}

	/**
	 * 暂停场景
	 *
	 * @param localScene 当地场景
	 * @return {@code Boolean }
	 * @throws
	 * @see LocalScene
	 * @see Boolean
	 */
	@Override
	public Boolean pauseScene(LocalScene localScene) {

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

	/**
	 * 取消暂停场景
	 *
	 * @param localScene 当地场景
	 * @return {@code Boolean }
	 * @throws
	 * @see LocalScene
	 * @see Boolean
	 */
	@Override
	public Boolean unpauseScene(LocalScene localScene) {

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

	/**
	 * 获取节点快照上下文
	 *
	 * @param localScene 当地场景
	 * @param context    上下文
	 * @return {@code NodeSnapshotContext }
	 * @throws
	 * @see LocalScene
	 * @see CreateSnapshotContext
	 * @see NodeSnapshotContext
	 */
	private NodeSnapshotContext getNodeSnapshotContext(LocalScene localScene,
			CreateSnapshotContext context) {
		SceneOpsContext opsContext = getSceneOpsContext(localScene);
		NodeSnapshotContext snapshotContext = BeanUtils.copy(opsContext, NodeSnapshotContext.class);
		org.springframework.beans.BeanUtils.copyProperties(context, snapshotContext);

		return snapshotContext;
	}

	/**
	 * 获取场景操作 计划上下文
	 *
	 * @param localScene 当地场景
	 * @return {@code SceneOpsContext }
	 * @throws
	 * @see LocalScene
	 * @see SceneOpsContext
	 */
	private SceneOpsContext getSceneOpsContext(LocalScene localScene) {
		SceneOpsContext sceneOpsContext = new SceneOpsContext();
		sceneOpsContext.setId(localScene.getMpSceneId());
		sceneOpsContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());
		sceneOpsContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_OPERATOR,
				Operator.BC.getDesc());

		return sceneOpsContext;
	}

	/**
	 * 获取节点操作 计划上下文
	 *
	 * @param localScene 当地场景
	 * @param nodeId     节点id
	 * @return {@code NodeOpsContext }
	 * @throws
	 * @see LocalScene
	 * @see String
	 * @see NodeOpsContext
	 */
	private NodeOpsContext getNodeOpsContext(LocalScene localScene, String nodeId) {
		NodeOpsContext nodeOpsContext = new NodeOpsContext();
		nodeOpsContext.setId(localScene.getMpSceneId());
		nodeOpsContext.setNodeId(nodeId);
		nodeOpsContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());

		return nodeOpsContext;
	}

	/**
	 * 获取流量操作 计划上下文
	 *
	 * @param localScene 当地场景
	 * @param nodeId     节点id
	 * @param flowId     流量id
	 * @return {@code FlowOpsContext }
	 * @throws
	 * @see LocalScene
	 * @see String
	 * @see String
	 * @see FlowOpsContext
	 */
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
	 *
	 * @param topoId
	 * @param enableRecording 启用录制
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	private void switchRecording(String topoId, Boolean enableRecording) {
		TopoSwitchRecordingContext context = new TopoSwitchRecordingContext(topoId,
				enableRecording);
		remoteTopoService.sync(JsonUtil.java2JsonObject(context));
	}

	/**
	 * 创建节点
	 *
	 * @param localSceneId 当地场景id
	 * @param nodeId       节点id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean createNode(String localSceneId, String nodeId) {

		LocalScene localScene = getById(localSceneId);
		if (localScene == null || StringUtils.isBlank(localScene.getMpSceneId())) {
			log.error("invalid localScene {}", localSceneId);
			throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
		}
		NodeOpsContext nodeOpsContext = getNodeOpsContext(localScene, nodeId);
		Boolean result = remoteSceneService.createNode(nodeOpsContext);
		if (!result) {
			log.error("failed to create node [{}] in scene [{}]", nodeId,
					localScene.getMpSceneId());
			throw new BusinessException(ErrorCode.FAILED_TO_CREATE_NODE);
		}
		return true;
	}

	/**
	 * 更新节点
	 *
	 * @param mpSceneId  中台场景id
	 * @param updateData 更新数据
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see JSONObject
	 * @see Boolean
	 */
	@Override
	public Boolean updateNode(String mpSceneId, JSONObject updateData) {

		LocalScene localScene = getOrExceptionByMpSceneId(mpSceneId);
		NodeUpdateContext context = getNodeUpdateContext(localScene, updateData);
		remoteSceneService.updateNode(context);
		return true;
	}

	/**
	 * 删除节点
	 *
	 * @param mpSceneId 中台场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean deleteNode(String mpSceneId, String nodeId) {
		LocalScene localScene = getOrExceptionByMpSceneId(mpSceneId);
		NodeOpsContext context = getNodeOpsContext(localScene, nodeId);
		remoteSceneService.deleteNode(context);
		return true;
	}

	/**
	 * 更新场景
	 *
	 * @param localScene  当地场景
	 * @param updateScene 更新场景
	 * @throws
	 * @see LocalScene
	 * @see JSONObject
	 */
	@Override
	public void updateScene(LocalScene localScene, JSONObject updateScene) {
		checkDemonstration();
		SceneUpdateContext context = getSceneUpdateContext(localScene);
		context.setUpdate_scene(updateScene);
		remoteSceneService.updateScene(context);
	}

	/**
	 * 起始节点
	 *
	 * @param mpSceneId 中台场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean startNode(String mpSceneId, String nodeId) {
		LocalScene localScene = getOrExceptionByMpSceneId(mpSceneId);
		NodeOpsContext context = getNodeOpsContext(localScene, nodeId);
		return remoteSceneService.startNode(context);
	}

	/**
	 * 停止节点
	 *
	 * @param mpSceneId 中台场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean stopNode(String mpSceneId, String nodeId) {
		LocalScene localScene = getOrExceptionByMpSceneId(mpSceneId);
		NodeOpsContext context = getNodeOpsContext(localScene, nodeId);
		return remoteSceneService.stopNode(context);
	}

	/**
	 * 重新启动节点由场景id
	 *
	 * @param mpSceneId 中台场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean restartNodeBySceneId(String mpSceneId, String nodeId) {
		LocalScene localScene = getOrExceptionByMpSceneId(mpSceneId);
		NodeOpsContext context = getNodeOpsContext(localScene, nodeId);
		return remoteSceneService.restartNode(context);
	}

	/**
	 * 获取资产列表由实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @param category           类别
	 * @param name               名称
	 * @param realTimeHostState  实时主机状态
	 * @return {@code JSONArray }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 * @see JSONArray
	 */
	@Override
	public JSONArray getAssetListByInstanceId(String businessInstanceId, Integer category,
			String name, Boolean realTimeHostState) {
		LocalScene localScene = getOrExceptionByBusinessInstanceId(businessInstanceId);
		SceneNodeListReq req = new SceneNodeListReq();
		req.setId(localScene.getMpSceneId());
		req.setCategory(category);
		req.setName(name);
		// 前台设备列表展示主机状态
		req.setRealTimeHostState(realTimeHostState);
		// 前台设备列表展示简易型
		req.setIsAll(true);
		return remoteSceneService.getAssetList(req);
	}

	/**
	 * 获取资产列表由拓扑id
	 *
	 * @param topoId   拓扑id
	 * @param category 类别
	 * @param name     名称
	 * @return {@code JSONArray }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see JSONArray
	 */
	@Override
	public JSONArray getAssetListByTopoId(String topoId, Integer category, String name) {
		TopoNodeListReq req = new TopoNodeListReq();
		req.setId(topoId);
		req.setCategory(category);
		req.setName(name);
		// 前台设备列表展示简易型
		req.setIsAll(true);
		return remoteTopoService.getAllNodes(req);
	}

	/**
	 * 获取节点统计学
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	@Override
	public JSONObject getNodeStatistics(String localSceneId) {
		String mpSceneId = getMpSceneIdOrExceptionByLocalSceneId(localSceneId);
		return remoteSceneService.getAssetsStatisticsBySceneId(mpSceneId);
	}

	/**
	 * 获取节点统计学由拓扑id
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	@Override
	public JSONObject getNodeStatisticsByTopoId(String topoId) {
		return remoteTopoService.getNodeStatistics(topoId);
	}

	/**
	 * 获取中台场景id由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String getMpSceneIdByBusinessInstanceId(String businessInstanceId) {
		LocalScene localScene = getOrExceptionByBusinessInstanceId(businessInstanceId);
		return localScene.getMpSceneId();
	}

	/**
	 * 获取资产列表
	 *
	 * @param req 请求体
	 * @return {@code List<JSONObject> }
	 * @throws
	 * @see SceneNodeListReq
	 * @see List<JSONObject>
	 */
	@Override
	public List<JSONObject> getAssetList(SceneNodeListReq req) {
		JSONArray rsp = remoteSceneService.getAssetList(req);
		return rsp.toJavaList(JSONObject.class);
	}

	/**
	 * 获取都资产由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code List<JSONObject> }
	 * @throws
	 * @see String
	 * @see List<JSONObject>
	 */
	@Override
	public List<JSONObject> getAllAssetByBusinessInstanceId(String businessInstanceId) {
		LocalScene localScene = getOrExceptionByBusinessInstanceId(businessInstanceId);
		SceneNodeListReq req = new SceneNodeListReq();
		req.setId(localScene.getMpSceneId());
		return remoteSceneService.getAssetList(req).toJavaList(JSONObject.class);
	}

	/**
	 * 获取中台场景id或例外由当地场景id
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	private String getMpSceneIdOrExceptionByLocalSceneId(String localSceneId) {
		LocalScene localScene = getOrException(localSceneId);
		String mpSceneId = localScene.getMpSceneId();
		if (StringUtils.isBlank(mpSceneId)) {
			log.error("local scene [{}] has no valid mpSceneId", localScene);
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		return mpSceneId;
	}

	/**
	 * 获取或例外
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code LocalScene }
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	private LocalScene getOrException(String localSceneId) {
		LocalScene localScene = localSceneMapper.selectById(localSceneId);
		if (null == localScene) {
			log.error("cant find localscene by localSceneId: [{}]", localSceneId);
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		return localScene;
	}

	/**
	 * 获取场景更新上下文
	 *
	 * @param localScene 当地场景
	 * @return {@code SceneUpdateContext }
	 * @throws
	 * @see LocalScene
	 * @see SceneUpdateContext
	 */
	private SceneUpdateContext getSceneUpdateContext(LocalScene localScene) {
		SceneUpdateContext sceneUpdateContext = new SceneUpdateContext();
		sceneUpdateContext.setId(localScene.getMpSceneId());
		sceneUpdateContext.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());
		return sceneUpdateContext;
	}

	/**
	 * 获取节点更新上下文
	 *
	 * @param localScene 当地场景
	 * @param updateData 更新数据
	 * @return {@code NodeUpdateContext }
	 * @throws
	 * @see LocalScene
	 * @see JSONObject
	 * @see NodeUpdateContext
	 */
	private NodeUpdateContext getNodeUpdateContext(LocalScene localScene, JSONObject updateData) {
		NodeUpdateContext context = new NodeUpdateContext();
		context.setId(localScene.getMpSceneId());
		// for mq message
		context.getContext().getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());
		// set update data
		context.setUpdate(updateData);
		return context;
	}

	/**
	 * 获取或例外由中台场景id
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code LocalScene }
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	@Override
	public LocalScene getOrExceptionByMpSceneId(String mpSceneId) {
		LocalScene localScene = localSceneMapper.selectBySceneId(mpSceneId);
		if (null == localScene) {
			log.error("cannot find a local_scene by scene_id: [{}]", mpSceneId);
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		return localScene;
	}

	/**
	 * 获取或例外由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene }
	 * @throws
	 * @see String
	 * @see LocalScene
	 */
	private LocalScene getOrExceptionByBusinessInstanceId(String businessInstanceId) {
		LocalScene localScene = localSceneMapper.selectByBusinessInstanceId(businessInstanceId);
		if (null == localScene) {
			log.error("cant find localscene by business_instance_id: [{}]", businessInstanceId);
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		return localScene;
	}

	/**
	 * 检查演示
	 *
	 * @throws
	 */
	private void checkDemonstration() {
		RemoteUserService remoteUserService= SpringContextUtil
				.getBeanByClass(RemoteUserService.class);
		Boolean demoOpen= remoteUserService.isDemoOpen();
		if (Boolean.TRUE.equals(demoOpen)
				&& Boolean.TRUE.equals(!ThreadLocalContext.whiteUpdateThreadLocal.get())) {//如果是演示模式且不允许访问数据库 直接返回

			throw new BusinessException(ErrorCode.DEMONSTRATION_FORBID_UPDATE);
		}
	}
}
