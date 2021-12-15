package com.cyberpeace.cr.crbase.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;

public interface SceneFilterService {

	void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes);
}
