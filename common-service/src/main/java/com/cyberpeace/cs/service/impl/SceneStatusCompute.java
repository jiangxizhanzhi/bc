package com.cyberpeace.cs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 场景状态计算
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class SceneStatusCompute {

	/**
	 * 计算场景状态
	 * @param scene
	 * @return
	 */
	public static LocalScene.SceneStatusEnum compute(JSONObject scene) {
		LocalScene.SceneStatusEnum[] constants = LocalScene.SceneStatusEnum.class
				.getEnumConstants();

		if (null == scene || scene.isEmpty()) {
			return LocalScene.SceneStatusEnum.INIT;
		}

		// 数据解析
		JSONObject sceneData = scene.getJSONObject(JsonKeyConstant.SCENE_DATA)
				.getJSONObject(JsonKeyConstant.TOPOLOGY).getJSONObject(JsonKeyConstant.SCENE_DATA);
		Integer sceneEvent = sceneData.getInteger(JsonKeyConstant.SCENE_EVENT);
		Integer sceneEventStatus = sceneData.getInteger(JsonKeyConstant.SCENE_EVENT_STATUS);

		// 循环判断
		for (LocalScene.SceneStatusEnum constant : constants) {
			if (!constant.isCurrent(sceneEvent, sceneEventStatus)) {
				continue;
			}
			return constant;
		}

		return LocalScene.SceneStatusEnum.INIT;
	}

}
