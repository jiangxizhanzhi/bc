package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.model.scene.TopoDigest;

import java.util.List;
import java.util.Map;

public interface RemoteTopoService {

	String create(SceneConfig sceneConfig);

	JSONObject getTopoById(String topoId);

	/**
	 * 根据现有的topoid，生成一份新的拓扑拷贝数据
	 *
	 * @param originTopoId 原始拓扑id
	 * @return 返回拷贝后的拓扑id
	 */
	String copyTopo(String originTopoId);

	TopoDigest getTopoDigest(String topoId);

	Map<String, TopoDigest> batchGetTopoDigest(List<String> topoIds);

	String save(JSONObject json);

	SceneConfig getById(String topoId);
}
