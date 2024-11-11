package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * 场景动态更新
 *
 * @author SN
 * @date 2023/05/11
 */
@Data
public class SceneUpdateContext extends SceneOpsContext implements Serializable {

	/**
	 * 更新场景
	 */
	private JSONObject update_scene;
}
