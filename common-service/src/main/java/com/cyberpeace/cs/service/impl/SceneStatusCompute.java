/**
 * ### 代码分析 
 * #### 功能概述： 
 * 该代码实现了一个用于计算场景状态的方法 `compute`。它接收一个 JSON 对象作为输入，并根据预定义的枚举值返回场景的状态。 
 * #### 主要功能模块： 
 * 1. **参数校验**： 
 * - 如果传入的 JSON 对象为空或为空对象，则直接返回初始状态 `INIT`。 
 * 2. **数据解析**： 
 * - 从 JSON 对象中提取与场景相关的数据（如 `scene_event` 和 `scene_event_status`），这些数据通过指定的键值获取。 
 * 3. **状态计算逻辑**： 
 * - 遍历预定义的枚举类 `LocalScene.SceneStatusEnum` 的所有常量。 
 * - 调用每个枚举实例的 `isCurrent` 方法，判断其是否适用于当前的 `scene_event` 和 `scene_event_status`。 
 * - 若找到匹配的状态，则返回对应的枚举值；否则返回默认的初始状态 `INIT`。 
 * #### 关键点： 
 * - **枚举类**：`LocalScene.SceneStatusEnum` 是一个自定义的枚举类，包含多种可能的场景状态。 
 * - **依赖注入**：方法依赖于 `JsonKeyConstant` 类中的常量来定位 JSON 中的数据字段。 
 * - **灵活性**：通过扩展枚举类的 `isCurrent` 方法，可以灵活地定义不同的状态计算逻辑。 
 * #### 总结： 
 * 该方法设计清晰，逻辑严谨，能够处理空输入和复杂的 JSON 数据结构，适合用于动态场景状态的计算场景。 
**/
package com.cyberpeace.cs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 场景状态计算
 *
 * @author SN
 * @date 2024/11/08
 */
public class SceneStatusCompute {

	/**
	 * 计算场景状态
	 *
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
