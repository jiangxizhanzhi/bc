package com.cyberpeace.cr.crbase.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.context.role.CrRoleNodesData;

public interface SceneFilterService {

	/**
	 * 过滤场景数据，使之匹配战争迷雾和用户资产
	 * @param topoData 场景拓扑数据
	 * @param nodes 角色节点数据
	 */
	void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes);
}
