package com.cyberpeace.cr.hld.service;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;

import java.util.List;

public interface HldSceneService {

	String startScene(String roomId, String userId);

	SceneViewContext viewScene(String roomId, String roleId);

	Boolean stopScene(String roomId);

	void clearLocal(HldRoom room);

	Boolean pauseScene(String roomId);

	Boolean unpauseScene(String roomId);

	List<Flow> getFlowList(String roomId);

}
