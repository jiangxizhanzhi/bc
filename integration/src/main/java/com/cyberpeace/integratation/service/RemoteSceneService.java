package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.scene.TopoDigest;

public interface RemoteSceneService {

	/**
	 * 获取拓扑的摘要信息。如拓扑中节点规模数等
	 * @param topoId
	 * @return
	 */
	TopoDigest getTopoDigest(String topoId);
}
