package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * 场景动态更新
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/11
 * @see SceneOpsContext
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneUpdateContext extends SceneOpsContext implements Serializable {

	/**
	 * 更新场景
	 *
	 * @see JSONObject
	 */
	private JSONObject update_scene;
}
