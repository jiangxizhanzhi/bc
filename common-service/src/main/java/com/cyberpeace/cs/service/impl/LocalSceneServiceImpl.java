package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalSceneServiceImpl extends ServiceImpl<LocalSceneMapper, LocalScene> implements LocalSceneService {

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Override
	public LocalScene getByBusinessInstanceId(String businessInstanceId) {
		LocalScene localScene = getBaseMapper().getByBusinessInstanceId(businessInstanceId);

		return localScene;
	}

	@Override
	public Boolean startScene(String localSceneId, SceneOpsContext sceneOpsContext) {
		LocalScene localScene = localSceneMapper.selectById(localSceneId);

		if (null == localScene) {
			// todo
			// 参数校验不通过的情况下，如何处理
			throw new RuntimeException();
		}

		String sceneId = "";
		// remoteSceneService.startScene(localScene.getTopoId(),
		// sceneOpsContext());
		LocalScene toUpdate = new LocalScene();

		toUpdate.setResourceId(localScene.getResourceId());
		toUpdate.setMpSceneId(sceneId);
		toUpdate.setStartTime(new Date());
		toUpdate.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		toUpdate.setUserId(UserInfoContext.getUserContext().getUserId());

		// TODO
		// 使用枚举
		if (localScene.getSceneTtl() != null && localScene.getDestoryMode() == 1) {
			toUpdate.setDestoryTime(
					TimeUtil.addMins(toUpdate.getStartTime(), localScene.getSceneTtl()));
		}

		localSceneMapper.updateById(toUpdate);

		return true;
	}

	@Override
	public Boolean closeScene(String localSceneId) {
		LocalScene localScene = localSceneMapper.selectById(localSceneId);

		Boolean result = remoteSceneService.stopScene(localScene.getMpSceneId(),
				getSceneStartContext());

		if (!result) {
			return result;
		}

		// todo
		// 更新localscene

		return null;
	}

	@Override
	public Boolean pauseScene(String localSceneId) {
		return null;
	}

	@Override
	public Boolean unPause(String localSceneId) {
		return null;
	}

	// TODO
	// 场景的启动、删除、暂停上下文是否可以统一
	private SceneStartContext getSceneStartContext() {
		SceneStartContext sceneStartContext = new SceneStartContext();

		sceneStartContext.setContext(new HashMap<>());
		sceneStartContext.setSceneAttr(SceneStartContext.SceneAttrEnum.SINGLE.getCode());

		return sceneStartContext;
	}

}
