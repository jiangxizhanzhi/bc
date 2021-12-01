package com.cyberpeace.oj.ojbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.SceneHandler;
import com.cyberpeace.integratation.model.scene.SceneMetaData;
import com.cyberpeace.integratation.model.scene.SceneMpContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;

@Service("OjSceneHandler")
public class OjSceneHandler extends SceneHandler {

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Override
	public Integer getBusinessType() {
		return super.getBusinessType();
	}

	/**
	 * 获取场景启动数据 用于启动场景
	 * @param localScene
	 * @return
	 */
	@Override
	public SceneStartContext getSceneStartContext(LocalScene localScene) {

		// 装配基本数据
		SceneStartContext sceneStartContext = super.getSceneStartContext(localScene);
		sceneStartContext.setId(localScene.getTopoId());
		sceneStartContext.setSceneTtl(localScene.getSceneTtl());

		// 查询实训场景信息 设置场景模式
		OjLocalScene ojLocalScene = ojLocalSceneMapper
				.selectById(localScene.getBusinessInstanceId());

		sceneStartContext.setSceneAttr(
				ojLocalScene.getShared() ? SceneStartContext.SceneAttrEnum.NULL.getCode()
						: SceneStartContext.SceneAttrEnum.SINGLE.getCode());

		// 获取元数据
		sceneStartContext
				.setReferenceMetaData(JsonUtil.toJsonString(getReferenceMetaData(ojLocalScene)));

		// 获取场景启动上下文
		sceneStartContext.setContext(getSceneMpContext(ojLocalScene));

		return sceneStartContext;
	}

	/**
	 * 获取场景启动元数据 用于前端场景列表
	 * @param ojLocalScene
	 * @return
	 */
	public SceneMetaData getReferenceMetaData(OjLocalScene ojLocalScene) {
		// 公有数据组装
		SceneMetaData sceneMetaData = new SceneMetaData();
		sceneMetaData.setBusinessInstanceId(ojLocalScene.getResourceId());
		return sceneMetaData;
	}

	/**
	 * 获取场景启动上下文 用户接收消息
	 * @return
	 */
	public SceneMpContext getSceneMpContext(OjLocalScene ojLocalScene) {
		return new SceneMpContext();
	}
}
