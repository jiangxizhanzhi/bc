package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;

@Service
public class SceneFilterServiceImpl implements SceneFilterService {

	@Override
	public void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes) {
		if (nodes == null || null == topoData) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		List<String> assetNodeIds = nodes.getAssetNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = topoData.getJSONObject("topology").getJSONArray("elements");
		int i = 0;
		while (i < elements.size()) {
			JSONObject element = elements.getJSONObject(i);
			String elementId = element.getString("id");
			if (!visibleNodeIds.contains(elementId)) {
				elements.remove(i);
			} else {
				// 非资产节点去除接入方式
				if (!assetNodeIds.contains(elementId)) {
					element.put("ports", Lists.newArrayList());
				}
				i++;
			}
		}
	}
}
