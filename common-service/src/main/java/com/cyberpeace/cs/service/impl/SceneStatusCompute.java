package com.cyberpeace.cs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.enums.SceneStatusEnum;

public class SceneStatusCompute {

	/**
	 * 计算场景状态
	 * @param scene
	 * @return
	 */
	public static SceneStatusEnum compute(JSONObject scene) {
		SceneStatusEnum[] constants = SceneStatusEnum.class
				.getEnumConstants();

		if (null == scene || scene.isEmpty()) {
			return SceneStatusEnum.INIT;
		}

		// 数据解析
		JSONObject sceneData = scene.getJSONObject(JsonKeyConstant.SCENE_DATA)
				.getJSONObject(JsonKeyConstant.TOPOLOGY).getJSONObject(JsonKeyConstant.SCENE_DATA);
		Integer sceneEvent = sceneData.getInteger(JsonKeyConstant.SCENE_EVENT);
		Integer sceneEventStatus = sceneData.getInteger(JsonKeyConstant.SCENE_EVENT_STATUS);

		// 循环判断
		for (SceneStatusEnum constant : constants) {
			if (Boolean.FALSE.equals(constant.isCurrent(sceneEvent, sceneEventStatus))) {
				continue;
			}
			return constant;
		}

		return SceneStatusEnum.INIT;
	}

	private SceneStatusCompute() {}

}
