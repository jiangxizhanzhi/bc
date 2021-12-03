package com.cyberpeace.cr.teamtrain.service;

import com.alibaba.fastjson.JSONObject;

public interface TeamSceneService {

	void startScene(String roomId, String userId);

	/**
	 * 查看场景
	 * @return
	 */
	JSONObject viewScene(String roomId, String roleId);

	void stopScene();
}
