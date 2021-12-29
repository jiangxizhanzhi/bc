package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;

public interface CommonSceneService {

	/**
	 * 获取监控会话
	 * @return
	 */
	List<MonitorSession> getMonitorSessions(String localSceneId, String studentId);
}
