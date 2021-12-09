package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.NodeOpsContext;
import com.cyberpeace.integratation.model.scene.ReferenceMetaData;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalSceneServiceImpl extends ServiceImpl<LocalSceneMapper, LocalScene> implements LocalSceneService {

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	@Qualifier("BasicSceneHandler")
	private SceneHandler sceneHandler;

	@Override
	public LocalScene getByBusinessInstanceId(String businessInstanceId) {
		LocalScene localScene = getBaseMapper().getByBusinessInstanceId(businessInstanceId);

		return localScene;
	}

	@Override
	public String startScene(LocalScene localScene) {
		SceneStartContext startContext = getSceneStartContext(localScene);

		return remoteSceneService.startScene(startContext);
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

		return referenceMetaData;
	}

	@Override
	public LocalScene getById(String localSceneId) {
		LocalScene localScene = localSceneMapper.selectById(localSceneId);

		return localScene;
	}

	@Override
	public Boolean stopScene(String localSceneId) {
		SceneOpsContext sceneOpsContext = getSceneOpsContext(
				localSceneMapper.selectById(localSceneId));

		Boolean result = remoteSceneService.stopScene(sceneOpsContext);

		return result;
	}

	@Override
	public void stopLocalScene(LocalScene scene, String userId) {

	}

	@Override
	public Boolean restartNode(String localSceneId, String nodeId) {
		LocalScene localScene = getById(localSceneId);
		NodeOpsContext nodeOpsContext = getNodeOpsContext(localScene, nodeId);
		Boolean result = remoteSceneService.restartNode(nodeOpsContext);
		if (!result) {
			log.error("failed to restart node [{}] in scene [{}]", nodeId,
					localScene.getMpSceneId());
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public Boolean recreateNode(String localSceneId, String nodeId) {
		LocalScene localScene = getById(localSceneId);
		NodeOpsContext nodeOpsContext = getNodeOpsContext(localScene, nodeId);
		Boolean result = remoteSceneService.recreateNode(nodeOpsContext);
		if (!result) {
			log.error("failed to recreate node [{}] in scene [{}]", nodeId,
					localScene.getMpSceneId());
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public Boolean startFlow(String localSceneId, String nodeId, String flowId) {
		return null;
	}

	@Override
	public Boolean pauseScene(String localSceneId) {
		LocalScene localScene = getById(localSceneId);
		// 1. 场景已暂停，返回true
		if (localScene.getSceneStatus().equals(LocalScene.SceneStatusEnum.PAUSE.getCode())) {
			return true;
		}
		// 2. 发送mp请求
		SceneHandler handler = sceneHandler.getSceneHandler(localScene.getBusinessCode());
		handler.checkPauseScene(localScene);
		Boolean result = remoteSceneService.pauseScene(getSceneOpsContext(localScene));

		if (!result) {
			log.error("failed to pause scene [{}]", localScene.getMpSceneId());
			throw new BusinessException("failed to pause scene [{}]");
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
			throw new BusinessException("failed to unpause scene [{}]");
		}
		// 3、修改本地数据
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		updateById(localScene);

		return true;
	}

	private void ensureScenePaused(LocalScene localScene) {
		if (!localScene.getSceneStatus().equals(LocalScene.SceneStatusEnum.PAUSE.getCode())) {
			log.error("scene [{}] is not paused", localScene.getMpSceneId());
			throw new BusinessException("scene [{}] is not paused");
		}
	}

	@Override
	public LocalScene createLocalScene(Integer businessCode, String topoId) {
		LocalScene localScene = new LocalScene();
		localScene.setBusinessCode(businessCode);
		localScene.setTopoId(topoId);
		localScene.setMpSceneId("");
		localSceneMapper.insert(localScene);

		return localScene;
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
	public void updateMpSceneId(LocalScene localScene, String sceneId) {
		localScene.setMpSceneId(sceneId);
		localSceneMapper.updateById(localScene);
	}

	private Map<String, String> getContext(String managerId) {
		Map<String, String> context = Maps.newHashMap();
		context.put("scene_manager_id", managerId);
		return context;
	}

	@Override
	public void deleteRemoteScene(LocalScene localScene) {
		SceneHandler handler = sceneHandler.getSceneHandler(localScene.getBusinessCode());
		SceneOpsContext sceneOpsContext = handler.getSceneOpContext(localScene);
		remoteSceneService.stopScene(sceneOpsContext);
	}

	private void updateAfterStartScene(LocalScene localScene, String sceneId) {
		localScene.setMpSceneId(sceneId);
		localScene.setStartTime(new Date());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		if (localScene.getSceneTtl() != null && localScene.getDestroyMode() == 1) {
			localScene.setDestroyTime(
					TimeUtil.addMins(localScene.getStartTime(), localScene.getSceneTtl()));
		}

		localSceneMapper.updateById(localScene);
	}

	private void updateAfterStopScene(LocalScene localScene) {
		localScene.setMpSceneId("");
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.DESTROY.getCode());
		localScene.setDestroyTime(new Date());
		updateById(localScene);
	}

	private SceneOpsContext getSceneOpsContext(LocalScene localScene) {
		SceneOpsContext sceneOpsContext = new SceneOpsContext();
		sceneOpsContext.setId(localScene.getMpSceneId());
		SceneHandler handler = sceneHandler.getSceneHandler(localScene.getBusinessCode());
		handler.setPassThrough(sceneOpsContext, localScene);

		return sceneOpsContext;
	}

	private NodeOpsContext getNodeOpsContext(LocalScene localScene, String nodeId) {
		NodeOpsContext nodeOpsContext = new NodeOpsContext();
		SceneHandler handler = sceneHandler.getSceneHandler(localScene.getBusinessCode());
		handler.setPassThrough(nodeOpsContext, localScene);
		nodeOpsContext.setId(localScene.getMpSceneId());
		nodeOpsContext.setNodeId(nodeId);

		return nodeOpsContext;
	}

}
