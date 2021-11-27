package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
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
	RemoteTopoService remoteTopoService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	@Qualifier("BasicSceneHandler")
	private SceneHandler sceneHandler;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Override
	public LocalScene getByBusinessInstanceId(String businessInstanceId) {
		LocalScene localScene = getBaseMapper().getByBusinessInstanceId(businessInstanceId);

		return localScene;
	}

	@Override
	public LocalScene getById(String localSceneId) {
		LocalScene localScene = localSceneMapper.selectById(localSceneId);
		if (null == localScene) {
			log.error("failed to get localScene with id [{}]", localSceneId);
			throw new RuntimeException();
		}
		return localScene;
	}

	@Override
	public Boolean startScene(String localSceneId, String userId) {
		LocalScene localScene = getById(localSceneId);
		SceneHandler handler = sceneHandler.getHandler(localScene.getBusinessCode());
		SceneStartContext sceneStartContext = getSceneStartContext(localScene);
		Boolean isTest = handler.isTest();
		String sceneId = remoteSceneService.startScene(sceneStartContext, isTest);

		if (StringUtils.isBlank(sceneId)) {
			log.error("failed to create scene with topo[{}]", localScene.getTopoId());
			throw new RuntimeException();
		}
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					updateAfterStartScene(localScene, sceneId);
					handler.afterCreateScene();
				}
			});
		} catch (Exception e) {
			log.error("db error when creating scene: ", e);
			deleteRemoteScene(localScene, userId);
			throw new RuntimeException();
		}

		return true;
	}

	@Override
	public Boolean stopScene(String localSceneId, String userId) {
		LocalScene localScene = getById(localSceneId);
		SceneHandler handler = sceneHandler.getHandler(localScene.getBusinessCode());
		SceneOpsContext sceneOpsContext = getSceneOpContext(localScene);

		Boolean result = remoteSceneService.stopScene(localScene.getMpSceneId(), sceneOpsContext);
		if (!result) {
			log.error("failed to stop scene [{}]", localScene.getMpSceneId());
			throw new RuntimeException();
		}
		updateAfterStopScene(localScene);
		handler.doAfterStopScene(localScene);

		return true;
	}

	@Override
	public void stopLocalScene(LocalScene scene, String userId) {

	}

	@Override
	public Boolean pauseScene(String localSceneId) {
		LocalScene localScene = getById(localSceneId);
		ensureSceneStarted(localScene);

		SceneHandler handler = sceneHandler.getHandler(localScene.getBusinessCode());
		handler.checkPauseScenePermission(localScene);

		Boolean result = remoteSceneService.pauseScene(localScene.getMpSceneId(),
				getSceneOpContext(localScene));
		if (!result) {
			log.error("failed to pause scene [{}]", localScene.getMpSceneId());
			throw new RuntimeException();
		}
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.PAUSE.getCode());
		updateById(localScene);
		handler.afterPauseScene(localScene);
		return true;
	}

	private void ensureSceneStarted(LocalScene localScene) {
		if (StringUtils.isBlank(localScene.getMpSceneId()) || localScene.getSceneStatus()
				.equals(LocalScene.SceneStatusEnum.DESTROY.getCode())) {
			log.error("LocalScene [{}] is not started", localScene.getResourceId());
			throw new RuntimeException();
		}
	}

	@Override
	public Boolean unPauseScene(String localSceneId) {
		LocalScene localScene = getById(localSceneId);
		ensureScenePaused(localScene);
		SceneHandler handler = sceneHandler.getHandler(localScene.getBusinessCode());
		handler.checkUnpauseScenePermission(localScene);

		Boolean result = remoteSceneService.unpauseScene(localScene.getMpSceneId(),
				getSceneOpContext(localScene));

		if (!result) {
			log.error("failed to unpause scene [{}]", localScene.getMpSceneId());
			throw new RuntimeException();
		}

		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		updateById(localScene);
		handler.afterUnpauseScene(localScene);
		return true;
	}

	private void ensureScenePaused(LocalScene localScene) {
		if (!localScene.getSceneStatus().equals(LocalScene.SceneStatusEnum.PAUSE.getCode())) {
			log.error("scene [{}] is not paused", localScene.getMpSceneId());
		}
	}

	@Override
	public LocalScene copyLocalScene(Integer businessType, String localSceneId, Boolean clone) {
		LocalScene localScene = localSceneMapper.selectById(localSceneId);
		String topoId = localScene.getTopoId();

		if (StringUtils.isBlank(topoId)) {
			throw new RuntimeException();
		}

		if (clone) {
			topoId = remoteTopoService.copyTopo(topoId);
		}
		return createLocalScene(businessType, topoId);
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
		LocalScene localScene = localSceneMapper.selectById(localSceneId);
		if (localScene == null) {
			throw new RuntimeException();
		}
		return getNodeCount(localScene);
	}

	@Override
	public JSONObject getSceneData(LocalScene localScene, String userId) {
		if (StringUtils.isNotBlank(localScene.getMpSceneId())) {
			JSONObject scene = remoteSceneService.getById(localScene.getMpSceneId(), userId);
			if (scene != null) {
				return scene.getJSONObject("scene");
			}
		}
		return null;
	}

	@Override
	public JSONObject getTopoData(LocalScene localScene) {
		return remoteTopoService.getTopoById(localScene.getTopoId()).getJSONObject("scene_config");
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
	public void deleteRemoteScene(LocalScene localScene, String userId) {
		SceneOpsContext sceneOpsContext = new SceneOpsContext();
		// sceneOpsContext.setContext(getContext(localScene.getResourceId()));
		// sceneOpsContext.setAccessUserId(userId);

		remoteSceneService.stopScene(localScene.getMpSceneId(), sceneOpsContext);
	}

	private void updateAfterStartScene(LocalScene localScene, String sceneId) {
		localScene.setMpSceneId(sceneId);
		localScene.setStartTime(new Date());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		if (localScene.getSceneTtl() != null && localScene.getDestoryMode() == 1) {
			localScene.setDestoryTime(
					TimeUtil.addMins(localScene.getStartTime(), localScene.getSceneTtl()));
		}

		localSceneMapper.updateById(localScene);
	}

	private void updateAfterStopScene(LocalScene localScene) {
		localScene.setMpSceneId("");
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.DESTROY.getCode());
		localScene.setDestoryTime(new Date());
		updateById(localScene);
	}

	// TODO
	// 场景的启动、删除、暂停上下文是否可以统一
	private SceneStartContext getSceneStartContext(LocalScene localScene) {
		SceneHandler handler = sceneHandler.getHandler(localScene.getBusinessCode());
		SceneStartContext sceneStartContext = new SceneStartContext();
		sceneStartContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		sceneStartContext.setReferenceMetaData(handler.getReferenceMetaData(localScene));
		sceneStartContext.setId(localScene.getTopoId());
		sceneStartContext.setSceneAttr(SceneStartContext.SceneAttrEnum.SINGLE.getCode());

		return sceneStartContext;
	}

	private SceneOpsContext getSceneOpContext(LocalScene localScene) {
		SceneOpsContext sceneOpsContext = new SceneOpsContext();
		sceneOpsContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		sceneOpsContext.setId(localScene.getMpSceneId());

		return sceneOpsContext;
	}

}
