package com.cyberpeace.cr.crbase.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

public interface SceneService {

	void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes);

	LocalScene createLocalScene(Integer businessCode, String roomId, String topoId);

	SceneStartContext makeSceneStartContext(LocalScene localScene);
}
