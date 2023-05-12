package com.cyberpeace.cs.service;

import com.cyberpeace.cs.controller.req.scene.SceneUpdateReq;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 场景动态更新时，处理一些靶场关联业务，
 * 如更新训练角色
 *
 * @author SN
 * @date 2023/05/12
 */
public interface SceneDynamicService {

	/**
	 * 更新训练
	 *
	 * @param localScene 场景
	 * @param req        请求体
	 */
	void updateTraining(LocalScene localScene, SceneUpdateReq req);
}
