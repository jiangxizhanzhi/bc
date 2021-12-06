package com.cyberpeace.cr.teamtrain.service;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;

public interface TeamSceneService {

	void startScene(String roomId, String userId);

	/**
	 * 查看场景
	 * @return
	 */
	SceneViewContext viewScene(String roomId, String roleId);

	Boolean stopScene(String roomId);

	void clearLocal(TeamRoom room);
}
