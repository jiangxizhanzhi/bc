package com.cyberpeace.cr.teamtrain.service;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.teamtrain.controller.rsp.Flow;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;

import java.util.List;

public interface TeamSceneService {

	void startScene(String roomId, String userId);

	/**
	 * 查看场景
	 * @return
	 */
	SceneViewContext viewScene(String roomId, String roleId);

	Boolean stopScene(String roomId);

	void clearLocal(TeamRoom room);

	Boolean pauseScene(String roomId);

	Boolean unpauseScene(String roomId);

	List<Flow> getFlowList(String roomId);
}
