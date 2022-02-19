package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;

public interface RbSceneService {

	String startScene(String roomId, String userId);

	/**
	 * 查看场景
	 * @return
	 */
	SceneViewContext viewScene(String roomId, String roleId);

	/**
	 * 无需认证的查看场景接口，不考虑战争迷雾
	 * 只在场景开启后返回
	 * @param roomId
	 * @return
	 */
	JSONObject viewSceneNoAuth(String roomId);

	Boolean stopScene(String roomId);

	void clearLocal(RbRoom room);

	Boolean pauseScene(String roomId);

	Boolean unpauseScene(String roomId);

	List<Flow> getFlowList(String roomId);
}
