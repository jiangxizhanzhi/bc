package com.cyberpeace.cs.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.NodeOpsContext;
import com.cyberpeace.integratation.model.scene.SceneMetaData;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.google.common.collect.Maps;

@Service(value = "BasicSceneHandler")
public class SceneHandler {

	private static Map<Integer, SceneHandler> register = Maps.newHashMap();

	@PostConstruct
	public void init() {
		if (null != getBusinessType()) {
			register.put(this.getBusinessType(), this);
		}
	}

	/**
	 * 获取场景类型 子类可重写该方法
	 * @return
	 */
	public Integer getBusinessType() {
		return null;
	}

	/**
	 * 获取SceneContextHandler
	 * @param businessType
	 * @return
	 */
	public SceneHandler getSceneHandler(Integer businessType) {
		return register.get(businessType);
	}

	/**
	 * 场景启动校验 子类可根据业务重写
	 * @param localScene
	 */
	public void checkStartScene(LocalScene localScene) {}

	/**
	 * 场景暂停校验 子类可根据业务重写
	 * @param localScene
	 */
	public void checkPauseScene(LocalScene localScene) {}

	/**
	 * 获取场景启动上下文
	 * @param localScene
	 * @return
	 */
	public SceneStartContext getSceneStartContext(LocalScene localScene) {
		return new SceneStartContext();
	}

	/**
	 * 获取场景操作基本上下文
	 * @param localScene
	 * @return
	 */
	public SceneOpsContext getSceneOpContext(LocalScene localScene) {
		return new SceneOpsContext();
	}

	/**
	 * 获取节点操作上下文
	 * @param localScene
	 * @return
	 */
	public NodeOpsContext getNodeOpsContext(LocalScene localScene) {
		return new NodeOpsContext();
	}

	public SceneMetaData getReferenceMetaData(LocalScene localScene) {
		return null;
	}

	/**
	 * 删除场景或删除localScene时清理各实例和模板的 localSceneId字段
	 * @param localScene 
	 */
	public void clearLocalScene(LocalScene localScene) {}
}
