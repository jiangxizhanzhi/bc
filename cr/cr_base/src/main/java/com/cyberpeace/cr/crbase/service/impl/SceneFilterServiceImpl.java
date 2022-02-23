package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
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
		// topo 为空
		if(elements.isEmpty()){
			return;
		}
		JSONObject elementPorts = topoData.getJSONObject("plugins").getJSONObject("cpcloud")
				.getJSONObject("element_ports");
		int i = 0;
		while (i < elements.size()) {
			JSONObject element = elements.getJSONObject(i);
			String elementId = element.getString("id");
			Integer elementType = element.getInteger("type");
			if (!visibleNodeIds.contains(elementId)) {
				elements.remove(i);
			} else {
				// 非资产节点去除接入方式
				// 去除终端的ports的access_modes
				if (!assetNodeIds.contains(elementId)
						&& elementType.equals(CrCommonConstants.TERMINAL_TYPE_CODE)) {
					JSONArray ports = element.getJSONArray("ports");
					for (int j = 0; j < ports.size(); j++) {
						JSONObject port = ports.getJSONObject(j);
						// 去掉 elements 的 access_modes
						port.put("access_modes", Lists.newArrayList());
						// 去掉 port 的 ip => 去掉重启重建按钮
						port.put("ip", null);
						// 去掉 plugins 的 access_modes
						String portId = port.getString("id");
						elementPorts.getJSONObject(portId).put("access_modes",
								Lists.newArrayList());
					}
				}
				i++;
			}
		}
	}
}
