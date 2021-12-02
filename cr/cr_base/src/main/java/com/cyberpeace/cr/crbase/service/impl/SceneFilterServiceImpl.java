package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNode;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.SceneFilterService;

@Service
public class SceneFilterServiceImpl implements SceneFilterService {

	@Override
	public void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes) {
		if (nodes == null || null == topoData) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = topoData.getJSONObject("topology").getJSONArray("elements");
		int i = 0;
		while (i < elements.size()) {
			if (!visibleNodeIds.contains(elements.getJSONObject(i).getString("id"))) {
				elements.remove(i);
			} else {
				i++;
			}
		}
	}
}
