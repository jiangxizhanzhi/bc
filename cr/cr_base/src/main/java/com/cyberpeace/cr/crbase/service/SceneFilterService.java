package com.cyberpeace.cr.crbase.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;

/**
 * 场景过滤器Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface SceneFilterService {

	/**
	 * 过滤场景数据，使之匹配战争迷雾和用户资产
	 *
	 * @param topoData 场景拓扑数据
	 * @param nodes    角色节点数据
	 */
	void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes);
}
