package com.cyberpeace.cr.hld.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.entity.HldRoomScene;

public interface HldSceneService {

	String startScene(String roomId, String userId);

	SceneViewContext viewScene(String roomId, CrRole role);

	Boolean stopScene(String roomId, Boolean checkPermission);

	void clearLocal(HldRoom room);

	Boolean pauseScene(String roomId);

	Boolean unpauseScene(String roomId);

	List<Flow> getFlowList(String roomId);

	JSONObject viewSceneNoAuth(String roomId);

	List<HldRoomScene> selectByRoomId(String roomId);

	JSONObject viewTopoNoAuth(String roomId);
}
