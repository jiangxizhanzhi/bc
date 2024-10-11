package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * 场景动态更新
 *
 * @author SN
 */
@Data
public class SceneUpdateContext extends SceneOpsContext implements Serializable {

	private JSONObject update_scene;
}
