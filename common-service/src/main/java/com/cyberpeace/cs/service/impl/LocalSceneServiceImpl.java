package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
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
	public Boolean startScene(String localSceneId) {
		LocalScene localScene = localSceneMapper.selectById(localSceneId);

		if (null == localScene) {
			// todo
			// 参数校验不通过的情况下，如何处理
			throw new RuntimeException();
		}

		String sceneId = remoteSceneService.startScene(localScene.getTopoId(),
				getSceneStartContext());
		LocalScene toUpdate = new LocalScene();

		toUpdate.setResourceId(localScene.getResourceId());
		toUpdate.setMpSceneId(sceneId);
		toUpdate.setStartTime(new Date());
		toUpdate.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		toUpdate.setUserId(UserInfoContext.getUserContext().getUserId());

		localSceneMapper.updateById(toUpdate);

		return true;
	}

	private SceneStartContext getSceneStartContext() {
		SceneStartContext sceneStartContext = new SceneStartContext();

		sceneStartContext.setContext(new HashMap<>());
		sceneStartContext.setSceneAttr(SceneStartContext.SceneAttrEnum.SINGLE.getCode());

		return sceneStartContext;
	}
}
