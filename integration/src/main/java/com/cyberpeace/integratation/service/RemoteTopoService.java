package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.SceneConfig;

public interface RemoteTopoService {

	String create(SceneConfig sceneConfig);

	SceneConfig getById(String sceneConfigId);

	/**
	 * 根据现有的topoid，生成一份新的拓扑拷贝数据
	 *
	 * @param originTopoId 原始拓扑id
	 * @return 返回拷贝后的拓扑id
	 */
	String copyTopo(String originTopoId);
}
