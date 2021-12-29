package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;

public interface CommonService {

	/**
	 * 获取监控会话
	 * @return
	 */
	List<MonitorSession> getMonitorSessions(String localSceneId, String studentId);

	/**
	 * 判断中台scene是否已经停止
	 * @param sceneData
	 * @return
	 */
	Boolean isStopped(JSONObject sceneData);
}
